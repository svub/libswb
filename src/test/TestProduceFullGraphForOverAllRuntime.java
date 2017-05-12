package test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import libswb.GraphWriter;
import libswb.LibSwb;
import libswb.graph.Graph;

import org.junit.Before;
import org.junit.Test;
import org.okkam.util.Chronometer;

public class TestProduceFullGraphForOverAllRuntime {

	private static final String SVENS_OID = "http://www.okkam.org/entity/ok923bf64b-3edf-4d0a-baf8-592db9f55689";
	private static final String STEFANOS_OID = "http://www.okkam.org/entity/ok42fe5511-c177-435a-8cf7-18b6a881d8b7";
	private static final String HEIKOS_OID = "http://www.okkam.org/entity/ok5f23a5ce-a683-4c4d-ae73-b78cdc17aec1";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// villazzano
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "proxy.unitn.it");
		System.getProperties().put("proxyPort", "3128");

		// viale verona 5, trento - aka sven
		// System.getProperties().put("proxySet", "true");
		// System.getProperties().put("proxyHost", "schweinehaxe");
		// System.getProperties().put("proxyPort", "8456");
		
		
	}

	@Test
	public void testPruning100NoThreads() {
		createGraph(100, false);
	}

	@Test
	public void testPruning100WithThreads() {
		createGraph(100, true);
	}
	
	@Test
	public void testCompareResults() {
		// TODO: compare to files produced from previous tests
		// manual test revealed, files contain the same info but triples are ordered differently
	}
	
	public void createGraph(int pruningLevel, boolean useTreads) {

		Chronometer chronometer = new Chronometer();

		boolean runQuiet = true;

		if (!runQuiet)
			System.out.println("produce full graph");

		LibSwb libswb = new LibSwb(pruningLevel, useTreads);

		long startTime = System.currentTimeMillis();
		libswb.gotoUri(SVENS_OID);
		libswb.gotoUri(STEFANOS_OID);
		Graph result = libswb.gotoUri(HEIKOS_OID);

		System.out.println("total runtime time:  "
				+ (System.currentTimeMillis() - startTime));
		libswb.printMergerStatistics();
		System.out.println("******* Final Result ********");
		System.out.println("time: "+chronometer.getCurrentTime());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GraphWriter writer = new GraphWriter(result.getModel(), "http://www.unitn.it", out);
		writer.writeGraph();
		if (!runQuiet) {
			System.out.println(out.toString());
		}
		String filename = "fullGraph-prunning" + pruningLevel;
		if (useTreads) {
			filename += "-usingThreads";
		}
		filename += ".rdf";
		try {
			FileOutputStream fileOS = new FileOutputStream(filename);
			fileOS.write(out.toByteArray());
			fileOS.close();
			System.out.println("graph written to " + filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("total time: "+chronometer.getCurrentTime());
	}
}
