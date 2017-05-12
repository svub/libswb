/**
 * 
 */
package test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import junit.framework.TestCase;
import libswb.GraphWriter;
import libswb.graph.Graph;
import libswb.graph.GraphFactory;
import libswb.modules.Merger;

/**
 * @author ste
 * 
 */
public class MergerTest extends TestCase {

	private Merger merger;
	private Model _model;
	private Model _model2;
	private static final String TEST_URI = "http://www.disi.unitn.it/#23456789023456789";
	private static final String URL = "http://disi.unitn.it/~bortoli/foaf.rdf"; // foaf.rdf
	private static final String TEST_HOME = "http://www.yahoo.com";
	private static final String TEST_HOME2 = "http://www.google.com";

	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
		super.setUp();
		/*
		 * System.getProperties().put("proxySet", "true");
		 * System.getProperties().put("proxyHost", "proxy.unitn.it");
		 * System.getProperties().put("proxyPort", "3128");
		 */
		_model = ModelFactory.createDefaultModel();
		_model2 = ModelFactory.createDefaultModel();

		Resource ppd = _model.createResource();

		Resource person = _model.createResource(TEST_URI);
		person.addProperty(RDF.type, FOAF.Person);

		Resource home = _model.createResource(TEST_HOME);
		person.addProperty(FOAF.homepage, home);

		ppd.addProperty(FOAF.primaryTopic, person);

		Resource ppd2 = _model2.createResource();

		Resource person2 = _model2.createResource(TEST_URI);
		person.addProperty(RDF.type, FOAF.Person);

		Resource home2 = _model2.createResource(TEST_HOME2);
		person.addProperty(FOAF.homepage, home2);

		ppd.addProperty(FOAF.primaryTopic, person2);
		merger = new Merger();
	}

	/**
	 * Test method for
	 * {@link libswb.modules.Merger#merge(libswb.graph.Graph, libswb.graph.Graph)}
	 * .
	 */
	public void testMerge() {
		Graph graph = merger.merge(
				GraphFactory.get().getGraph(_model),
				GraphFactory.get().getGraph(_model2));
		GraphWriter writer = new GraphWriter(graph.getModel(), TEST_URI,
				System.out);
		writer.writeGraph();
	}

}
