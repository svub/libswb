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

package libswb.graph;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class GraphFactory {

	public static ArrayList<String> urlList = new ArrayList<String>();
	
	private static GraphFactory singleton;

	public static GraphFactory get() {
		if (singleton == null) {
			singleton = new GraphFactory();
		}
		return singleton;
	}
	
	public GraphFactory() {
		// reset statistics
		urlList = new ArrayList<String>();
	}

	@Deprecated
	public Graph getGraph(String uri) {
		Model model = ModelFactory.createDefaultModel();
		model.read(uri);
		urlList.add(uri);
		return new Graph(model);
	}

	@Deprecated
	public Graph getGraph(InputStream in, String base) {
		Model model = ModelFactory.createDefaultModel();
		model.read(in, base);
		return new Graph(model);
	}
	
	public Graph getGraph(Model model) {
		return new Graph(model);
	}

	/*public Graph getGraph(URL url) throws IOException {
		return getGraph(url.openStream(), url.toString());
	}*/

}
