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
package test;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import libswb.GraphWriter;
import libswb.LibSwb;
import libswb.LibswbUtils;
import libswb.graph.Graph;
import libswb.graph.GraphFactory;
import libswb.modules.Merger;

import org.junit.*;
import org.okkam.util.UrlUtils;

/**
 * @author Sven Buschbeck
 * 
 */
public class TestOverAllRuntime {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		// villazzano
//		System.getProperties().put("proxySet", "true");
//		System.getProperties().put("proxyHost", "proxy.unitn.it");
//		System.getProperties().put("proxyPort", "3128");

		// viale verona 5, trento - aka sven@home
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "schweinehaxe");
		System.getProperties().put("proxyPort", "8456");
	}

	@Test
	public void run() {
		System.out.println(" over all runtime test\n---------------------------\n\n");

		CsvHelper results = new CsvHelper(new String[] { "index", "merging steps", "merged documents", "avg doc size",
				"total docs size", "avg num of nodes per doc", "total num of nodes in docs",
				"num of nodes in merged doc", "avg num of statements per doc", "total num of statements in docs",
				"statements in merged doc", "total runtime", "time for downloading" });

		boolean runQuiet = true;

		// wait for jvm to boot up completely
		try {
			Thread.sleep(2000);
			System.out.println(UrlUtils.readUrlAsString("http://www.hs-augsburg.de/~bucks/foaf.rdf").length());
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int testIndex = 0; testIndex < 1; testIndex++) {

			System.out.println("step " + testIndex + "...");
			LibSwb libswb = new LibSwb();
			libswb.useThreads = true;

			if (!runQuiet)
				System.out.println("testChangePivotUri...");
			long startTime, stepStartTime;

			startTime = stepStartTime = System.currentTimeMillis();
			libswb.gotoUri("http://www.okkam.org/entity/ok923bf64b-3edf-4d0a-baf8-592db9f55689");

			if (!runQuiet) {
				long initTime = System.currentTimeMillis() - stepStartTime;
				System.out.println("init time:   " + initTime);
				System.out.println("urls loaded: " + GraphFactory.urlList);
				libswb.printMergerStatistics();
				
				System.out.println("go to stefano");
				stepStartTime = System.currentTimeMillis();
			}

			libswb.gotoUri("http://www.okkam.org/entity/ok42fe5511-c177-435a-8cf7-18b6a881d8b7");

			if (!runQuiet) {
				long changeTime = System.currentTimeMillis() - stepStartTime;
				System.out.println("change time: " + changeTime);
				
				System.out.println("go to heiko");
				stepStartTime = System.currentTimeMillis();
			}
			
			Graph result = libswb.gotoUri("http://www.okkam.org/entity/ok5f23a5ce-a683-4c4d-ae73-b78cdc17aec1");

			if (!runQuiet) {
				long changeTime = System.currentTimeMillis() - stepStartTime;
				System.out.println("change time: " + changeTime);
			}

			long totalRuntime = (System.currentTimeMillis() - startTime);

			if (!runQuiet) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				GraphWriter writer = new GraphWriter(result.getModel(), "http://www.unitn.it", out);
				writer.writeGraph();
				System.out.println("******* Final Result ********");
				System.out.println(out.toString());

				libswb.printMergerStatistics();
			}

			// print once per loop anyway
			System.out.println("total time:  " + totalRuntime);
			libswb.printMergerStatistics();

			if (!runQuiet) {
				System.out.println("\ncalculating download time for urls loaded: " + GraphFactory.urlList);
			}

			int totalBytes = 0;
			startTime = System.currentTimeMillis();
			for (String url : GraphFactory.urlList) {
				int currentBytes = UrlUtils.readUrlAsString(url).length();
				// System.out.println(" url: "+url+"\nbytes: "+currentBytes);
				totalBytes += currentBytes;
			}

			long downloadRuntime = System.currentTimeMillis() - startTime;

			if (!runQuiet) {
				System.out.println("\ntotal bytes: " + totalBytes + " in " + GraphFactory.urlList.size() + " files.");
				System.out.println("time used to download: " + downloadRuntime);
			}

			// --- log to csv ---
			int numOfDocs = GraphFactory.urlList.size();
			results.addNext(testIndex);
			// # of merges
			results.addNext(Merger.mergeCount);
			// # merged docs
			results.addNext(numOfDocs);
			// avg doc size
			results.addNext(totalBytes * 1.0 / numOfDocs);
			// total docs size
			results.addNext(totalBytes);
			// TODO avg num of nodes per doc
			results.addNext(0);
			// TODO total num of nodes in docs
			results.addNext(0);
			// TODO num of nodes in merged doc
			results.addNext(0);
			// avg num of statements per doc
			results.addNext(Merger.statementsTotalIn / numOfDocs);
			// total num of statements in docs
			results.addNext(Merger.statementsTotalIn);
			// statements in merged doc
			results.addNext(Merger.statementsMaxOut);
			// total runtime
			results.addNext(totalRuntime);
			// time for downloading
			results.addNext(downloadRuntime);
		}

		String csv = results.getCsvWithHeader();
		System.out.println(csv);
		FileOutputStream os;
		try {
			os = new FileOutputStream("results.csv");
			os.write(csv.getBytes());
			os.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
