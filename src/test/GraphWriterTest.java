/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import libswb.GraphWriter;

import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author ste
 *
 */
public class GraphWriterTest {
	
	private Model _model;
	private GraphWriter graphWriter;
    private static final String TEST_URI = "http://disi.unitn.it/~bortoli/foaf.rdf";
    private static final String URL = "http://disi.unitn.it/~bortoli/foaf.rdf"; //foaf.rdf
    private static final String TEST_HOME = "http://www.yahoo.com";

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		/*System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "proxy.unitn.it");
		System.getProperties().put("proxyPort", "3128"); */
		_model = ModelFactory.createDefaultModel();
        
        Resource ppd = _model.createResource(TEST_URI);
        ppd.addProperty(RDF.type, FOAF.PersonalProfileDocument);
       
        Resource person = _model.createResource();
        person.addProperty(RDF.type, FOAF.Person);
        
        Resource home = _model.createResource(TEST_HOME);
        person.addProperty(FOAF.homepage, home);
        
        ppd.addProperty(FOAF.primaryTopic, person);                
	}

	/**
	 * Test method for {@link libswb.GraphWriter#GraphWriter(com.hp.hpl.jena.rdf.model.Model, java.lang.String, java.io.File)}.
	 */
	@Test
	public void testGraphWriterModelStringFile() {
		File file = new File("tmp");
		try {
			graphWriter = new GraphWriter(_model,URL,file);
			graphWriter.writeGraph();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line="";
			String fileContent="";
			while((line =reader.readLine())!=null) {
				fileContent+=line;
				fileContent+="\n";
			}
			reader.close();
			System.out.println("Write to FILE: \n"+fileContent);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link libswb.GraphWriter#GraphWriter(com.hp.hpl.jena.rdf.model.Model, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGraphWriterModelStringString() {
		File file = new File("test.txt");
		String graph=file.getName();
		try {
			graphWriter = new GraphWriter(_model,URL,graph);
			graphWriter.writeGraph();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line="";
			String fileContent="";
			while((line =reader.readLine())!=null) {
				fileContent+=line;
				fileContent+="\n";
			}
			reader.close();
			System.out.println("Write to FILE NAME: \n"+fileContent);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link libswb.GraphWriter#GraphWriter(com.hp.hpl.jena.rdf.model.Model, java.lang.String, java.io.OutputStream)}.
	 */
	@Test
	public void testGraphWriterModelStringOutputStream() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		graphWriter = new GraphWriter(_model,URL,stream);
		graphWriter.writeGraph();
		String content = stream.toString();
		System.out.println("Write to output stream:"+content);
	}

}
