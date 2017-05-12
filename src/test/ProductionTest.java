/**
 * 
 */
package test;

import static org.junit.Assert.*;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import libswb.*;
import libswb.graph.Graph;
import libswb.graph.GraphFactory;
import libswb.modules.Production;

import org.junit.Before;
import org.junit.Test;


/**
 * @author ste
 *
 */
public class ProductionTest {
	
	private GraphFactory factory;
	private GraphWriter writer;
	private Production production;
	private String pivotURI="http://www.okkam.org/entity/ok42fe5511-c177-435a-8cf7-18b6a881d8b7";
	private String pivotURI2="http://www.newid2.com";
	private static final String URL = "http://disi.unitn.it/~bortoli/";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		/*System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "proxy.unitn.it");
		System.getProperties().put("proxyPort", "3128"); */
		
		factory = new GraphFactory();
		production = new Production();
	}

	/**
	 * Test method for {@link libswb.modules.Production#prune(libswb.graph.Graph, java.lang.String, int)}.
	 */
	@Test
	public void testPrune() {
		Graph graph=null;
		try {
			File file = new File("src/test/foaf-test-prune.rdf");
			System.out.println(file.getAbsolutePath());
			System.out.println("file exists:" + file.exists());
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			graph = factory.getGraph(in,"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Graph result = production.prune(graph, pivotURI, 2);
		writer = new GraphWriter(result.getModel(),URL,System.out);
		writer.writeGraph();
	}

}
