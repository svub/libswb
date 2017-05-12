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

import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Sven Buschbeck & Stefano Bortoli
 * 
 * encapsulates Jena Model to be easily able to switch RDF framework in the future
 * 
 */
public class Graph {

	//public static int totalXmlBytesParsed;
	
	private Model model;
	//public int fileSize;
	
	Graph(Model model) {
		this.model = model;
	}

	/**
	 * @param queryResult
	 */
	/*public Graph(String xml) {
		// build a model reading the string
		fileSize = xml.length();
		totalXmlBytesParsed += fileSize;
		_model.read(xml);
	}*/
	
	
	public Model getModel() {
		return model;
	}

	/*public void set_model(Model _model) {
		this._model = _model;
	}*/
	
	public long getNumberOfStatement() {
		return model.size();
	}

}
