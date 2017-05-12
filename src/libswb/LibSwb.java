/**
 *  libswb
 *  Copyright (C) 2008  Sven Buschbeck & Stefano Bortoli
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program (LICENSE.txt).  
 *  If not, see <http://www.gnu.org/licenses/>
 *
 */
package libswb;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import libswb.graph.Graph;
import libswb.graph.GraphFactory;
import libswb.modules.UrlsProvider;
import libswb.modules.Merger;
import libswb.modules.Okkam;
import libswb.modules.Production;
import libswb.modules.Sindice;

import org.apache.log4j.Logger;
import org.okkam.core.data.api.MatchingCandidate;

/**
 * @author Sven Buschbeck
 * 
 */
public class LibSwb {

	public int pruningLevel;
	public boolean useThreads;

	// threshold for selection over multiple candidates
	private static final double OKKAM_THRESHOLD = 0.8;

	private Okkam okkam = new Okkam();
	private UrlsProvider urlProvider = new UrlsProvider();
	private Merger merger = new Merger();
	private Production production = new Production();
	private GraphWriter writer;
	private GraphFactory factory = new GraphFactory();

	private Graph currentGraph;
	private String currentUri;

	private Logger log = Logger.getLogger(this.getClass());

	public LibSwb() {
		this(1, true);
	}

	public LibSwb(int pruningLevel, boolean useThreads) {
		log.info("starting LibSwb: pruning " + pruningLevel
				+ (useThreads ? ", using threads" : ""));
		this.pruningLevel = pruningLevel;
		this.useThreads = useThreads;
	}

	public String searchUri(String keywords) {

		String result = okkam.query(keywords);
		// TODO could we simply return a List with oids instead of all this
		// if/else and those constants?

		String okkamId = null;
		if (result.equals(LibswbUtils.ONE_ENTITY_MATCHING)) {
			okkamId = okkam.getOkkamId();
		} else if (result.equals(LibswbUtils.SEVERAL_OKKAM_ENTITIES_RETURNED)) {
			// decide whether we take the first one or ask for selection
			List<MatchingCandidate> okkamCandidates = okkam
					.getEntityList(OKKAM_THRESHOLD);
			okkamId = okkamCandidates.get(0).getOid();
		} else if (result.equals(LibswbUtils.NO_OKKAM_ENTITY_RETURNED)) {
			// manage query with no entity response
		}

		return okkamId;
	}

	public Graph gotoUri(String newPivotUri) {
		currentUri = newPivotUri;

		printGraph("entering goto(" + newPivotUri + ")");

		// List<String> rdfUrls = sindice.query(newPivotUri);
		List<String> urlsToRdfFiles = urlProvider.UrlsForUri(newPivotUri);
		if (useThreads) {
			loadAndMergeViaThreads(urlsToRdfFiles, newPivotUri);
		} else {
			loadAndMerge(urlsToRdfFiles, newPivotUri);
		}

		// TODO: should not the currentGraph be pruned an saved as currentGraph?
		// return currentGraph;
		return production.prune(currentGraph, newPivotUri, pruningLevel);
	}

	// getters and setters ////////////////////////////////////////////////////

	public String getCurrentUri() {
		return currentUri;
	}

	// internals //////////////////////////////////////////////////////////////

	private void loadAndMerge(List<String> urlsToRdfFiles, String privotUri) {
		for (String urlToRdfFile : urlsToRdfFiles) {
			Graph newGraph = factory.getGraph(urlToRdfFile);
			mergeIn(prune(newGraph, privotUri));
		}
	}

	private void loadAndMergeViaThreads(List<String> urlsToRdfFiles,
			String privotUri) {
		List<Thread> threadList = new LinkedList<Thread>();
		List<GraphReceiver> modelReceivers = new LinkedList<GraphReceiver>();
		for (String urlToRdfFile : urlsToRdfFiles) {
			GraphReceiver modelReceiver = new GraphReceiver(urlToRdfFile);
			modelReceivers.add(modelReceiver);
			Thread thread = new Thread(modelReceiver);
			threadList.add(thread);
			thread.start();
		}

		int activeCount;
		do {
			activeCount = 0;
			for (GraphReceiver modelReceiver : modelReceivers) {
				if (modelReceiver.isRunning()) {
					activeCount++;
				} else {
					mergeIn(prune(modelReceiver.getModel(), privotUri));
				}
			}

			try {
				// TODO: what could be a good amount of timeout to put here?
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// log.info("wait for RDF downloads: "+activeCount+" thread(s) still active");
		} while (activeCount > 0);
	}

	/*
	 * private Graph prune(Graph graph) { return prune(graph, currentUri); }
	 */
	private Graph prune(Graph graph, String pivotUri) {
		return production.prune(graph, pivotUri, pruningLevel);
	}

	private void mergeIn(Graph graphToBeMergeToCurrent) {
		// TODO: TEST: we could test to prune each graph here already to
		// improve merging speed maybe
		if (currentGraph == null) {
			currentGraph = graphToBeMergeToCurrent;
		} else {
			currentGraph = merger.merge(currentGraph, graphToBeMergeToCurrent);
		}
	}

	/*
	 * private void mergeIn(List<Graph> graphsToBeMergeToCurrent) { for (Graph
	 * graph : graphsToBeMergeToCurrent) { mergeIn(graph); } }
	 */

	// performance measurement ////////////////////////////////////////////////
	/**
	 * @param graph
	 */
	public void printGraph() {
		printGraph("");
	}

	public void printGraph(String message) {
		printGraph("", currentGraph);
	}

	private void printGraph(String message, Graph graph) {
		try {
			if (log.isInfoEnabled()) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				writer = new GraphWriter(graph.getModel(),
						"http://www.unitn.it", out);
				writer.writeGraph();
				log.info(message + ": " + out.toString());
				out.close();
			}
		} catch (Exception e) {
		}
	}

	public void printMergerStatistics() {
		System.out.println("merger statistics:");
		System.out.println("statements in      " + Merger.statementsTotalIn);
		System.out.println("statements out     " + Merger.statementsTotalOut);
		System.out.println("statements max out " + Merger.statementsMaxOut
				+ "\n");
	}

}
