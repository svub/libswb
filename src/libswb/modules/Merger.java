/**
 *  libswb
 *  Copyright (C) 2008  Sven Buschbeck
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
package libswb.modules;

import libswb.graph.Graph;
import libswb.graph.GraphFactory;

import org.apache.log4j.Logger;


/**
 * @author Sven Buschbeck & Stefano Bortoli
 * 
 */
public class Merger {
	
	public static int mergeCount;
	public static long statementsTotalIn;
	public static long statementsTotalOut;
	public static long statementsMaxOut;
	
	private Logger log = Logger.getLogger(this.getClass());

	public Merger() {
		// reset statistics
		mergeCount = 0;
		statementsTotalIn = 0;
		statementsTotalOut = 0;
		statementsMaxOut = 0;
	}
	
	/**
	 * @param currentGraph
	 * @param newGraph
	 */
	public Graph merge(Graph baseGraph, Graph newGraph) {
		// apply jena merging

		mergeCount++;
		statementsTotalIn += newGraph.getNumberOfStatement();
		log.debug("merger: base graph size in: " + baseGraph.getNumberOfStatement());

		Graph graph = GraphFactory.get().getGraph(baseGraph.getModel().union(newGraph.getModel()));

		statementsTotalOut += graph.getNumberOfStatement();
		if (graph.getNumberOfStatement() > statementsMaxOut) {
			statementsMaxOut = graph.getNumberOfStatement();
		}
		log.debug(" out: " + graph.getNumberOfStatement());

		return graph;
	}

}
