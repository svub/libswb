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

package libswb;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import libswb.graph.Graph;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFSyntax;
import com.hp.hpl.jena.vocabulary.RSS;

public class GraphWriter {
	//checkout the value of these attributes..
	public static final String ADMIN_URI = "http://webns.net/mvcb/";
	public static final String ADMIN_AGENT = "generatorAgent";
	public static final String ADMIN_ERRORS = "errorReportsTo";
	public static final String API_URI = "http://www.okkam.org/libswb-project/";
	public static final String CONTACT_URI = "mailto:bortoli@disi.unitn.it";

	private OutputStream _out;
	private String _baseURI;
	private Model _model;

	public GraphWriter(Model model, String baseURI, File file)
			throws FileNotFoundException {
		_baseURI = baseURI;
		_out = new FileOutputStream(file);
		_model = model;
	}

	public GraphWriter(Model model, String baseURI, String filename)
			throws FileNotFoundException {
		_baseURI = baseURI;
		_out = new FileOutputStream(filename);
		_model = model;
	}

	public GraphWriter(Model model, String baseURI, OutputStream out) {
		_baseURI = baseURI;
		_out = out;
		_model = model;
	}


	private void generateAdditionalMetadata() {
		Resource theFile = _model.createResource(_baseURI);
		Resource thisApp = _model.createResource(API_URI);
		Resource mailto = _model.createResource(CONTACT_URI);

		theFile.addProperty(_model.createProperty(ADMIN_URI, ADMIN_AGENT),
				thisApp);
		theFile.addProperty(_model.createProperty(ADMIN_URI, ADMIN_ERRORS),
				mailto);

		Date now = Calendar.getInstance().getTime();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		theFile.addProperty(DC.date, fmt.format(now));
	}

	public void writeGraph() {
		generateAdditionalMetadata();

		try {
			if (_out != null) {

				getWriter().write(_model, _out, _baseURI);
				return;
			}

		} finally {
			try {
				if (_out != null)
					_out.close();
			} catch (Exception e) {
				//manage exception
			}
		}
	}

	private RDFWriter getWriter() {
		RDFWriter writer = _model.getWriter("RDF/XML-ABBREV");
		configurePrefixMappings(writer);
		return _model.getWriter();
	}

	private void configurePrefixMappings(RDFWriter writer) {
		_model.getGraph().getPrefixMapping().setNsPrefix("dc", DC.getURI());
		_model.getGraph().getPrefixMapping().setNsPrefix("rss", RSS.getURI());

		writer.setProperty("relativeURIs", "");
		writer.setProperty("showXmlDeclaration", Boolean.TRUE);
		writer.setProperty("blockRules",
				new Resource[] { RDFSyntax.propertyAttr,
						RDFSyntax.parseTypeResourcePropertyElt});

	}
	
	public static void write(Graph graph) {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		new GraphWriter(graph.getModel(), "http://www.unitn.it", os).writeGraph();
		System.out.println(new String(os.toByteArray()));
	}
}
