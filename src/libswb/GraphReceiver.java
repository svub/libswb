package libswb;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import libswb.graph.Graph;
import libswb.graph.GraphFactory;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class GraphReceiver implements Runnable {

	private URL url;
	private Model model;
	private int contentLength;
	private boolean done = false;
	
	public GraphReceiver(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		model = ModelFactory.createDefaultModel();
		try {
			URLConnection connection = url.openConnection();
			contentLength = connection.getContentLength();
			model.read(connection.getInputStream(), url.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		done = true;
	}

	public int getBytesRead() {
		return contentLength;
	}

	public Graph getModel() {
		return GraphFactory.get().getGraph(model);
	}
	
	public boolean isRunning() {
		//return Thread.currentThread().isAlive();
		return !done;
	}

}
