/**
 * 
 */
package test;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;



import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

import junit.framework.TestCase;
import libswb.graph.Graph;
import libswb.graph.GraphFactory;

/**
 * @author ste
 *
 */
public class GraphFactoryTest extends TestCase {
	
	private GraphFactory factory;

	private String url="http://www.disi.unitn.it/~bouquet/foaf.rdf";
	private Model _model;
    private static final String TEST_URI = "http://www.example.com/libswb/test";
    private static final String TEST_HOME = "http://www.libswb.com";
    private String _testRDF;
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(GraphFactoryTest.class);
    }

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		/*System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "proxy.unitn.it");
		System.getProperties().put("proxyPort", "3128"); */
		
		factory = new GraphFactory();
		
		_model = ModelFactory.createDefaultModel();
        
        Resource ppd = _model.createResource(TEST_URI);
        ppd.addProperty(RDF.type, FOAF.PersonalProfileDocument);
       
        Resource person = _model.createResource();
        person.addProperty(RDF.type, FOAF.Person);
        
        Resource home = _model.createResource(TEST_HOME);
        person.addProperty(FOAF.homepage, home);
        
        ppd.addProperty(FOAF.primaryTopic, person);               
        
        StringWriter writer = new StringWriter();
        _model.write(writer);
        _testRDF = writer.toString();
        writer.close();
	}


	/**
	 * Test method for {@link libswb.graph.GraphFactory#getGraph(java.lang.String)}.
	 */
	public void testGetGraphString() {
		Graph graph = factory.getGraph(url);
		System.out.println("string reader: "+graph.toString());
	}

	/**
	 * Test method for {@link libswb.graph.GraphFactory#getGraph(java.io.InputStream, java.lang.String)}.
	 */
	public void testGetGraphInputStreamString() {
		BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(_testRDF.getBytes()));
		Graph graph = factory.getGraph(in, "");
		System.out.println("input stream reader: "+graph.toString());
	}

	/**
	 * Test method for {@link libswb.graph.GraphFactory#getGraph(java.net.URL)}.
	 */
/*	public void testGetGraphURL() {
		try {
			URL location = new URL(url);
			Graph graph = factory.getGraph(location);
			System.out.println("url reader: "+graph.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
}
