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
package libswb.modules;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.okkam.util.UrlUtils;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

/**
 * @author Sven Buschbeck
 * 
 */
public class Sindice implements UrlsProviderModule {

	private Logger log = Logger.getLogger(this.getClass());
	
	private DocumentBuilderFactory factory;
	private DocumentBuilder newDowBuilder;

	public Sindice() {
	 	factory = DocumentBuilderFactory.newInstance();
	 	factory.setIgnoringComments(true);
	 	factory.setIgnoringElementContentWhitespace(true);
		try {
			newDowBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e2) {
			log.error("Sindice: xml parser not configured properly!");
		}
	}
	
	// DocumentProviderModule interface ///////////////////////////////////////

	public List<String> retrieveUrls(String keywords) {
		log.info("Sindice.retrieveUrls: keywords = "+keywords);
		
		List<String> rdfUrls = new LinkedList<String>();
		if (newDowBuilder == null) {
			log.error("Sindice.retrieveUrls: xml parser not configured properly!");
			return rdfUrls;
		}
		
		// TODO can we do a advanced search here?
		keywords = keywords.replace(" ", "+");
		String queryResult = getXml(keywords);
		
		try {
			Document d = newDowBuilder.parse(new InputSource(new StringReader(queryResult)));
			NodeList links = d.getElementsByTagName("link");
			for (int nodeIndex = 0; nodeIndex < links.getLength(); nodeIndex++) {
				Node link = links.item(nodeIndex);
				if (!link.getParentNode().getNodeName().equals("entry")) {
					continue;
				}
				String url = "";
				//try {
					url = link.getAttributes().getNamedItem("href")
							.getNodeValue();
					//rdfUrls.add(new URL(url));
					rdfUrls.add(url);
				/*} catch (Exception e) {
					log.error("URL malformed: " + url);
				}*/

			}
		} catch (SAXException e1) {
			log.error("could not parse xml response. xml:\n"+queryResult);
			//e1.printStackTrace();
		} catch (IOException e1) {
			log.error("could not read xml response. xml:\n"+queryResult);
			//e1.printStackTrace();
		}

		return rdfUrls;
	}

	// internals //////////////////////////////////////////////////////////////

	/**
	 * @param okkamId
	 * @return
	 */
	private String getXml(String query) {
		return getXml(query, false);
	}

	/**
	 * @param okkamId
	 * @return
	 */
	private String getXml(String query, boolean advanced) {
		// use keyword search - maybe we can use the adv search to get better
		// results
		String requestUrl = "http://api.sindice.com/v2/search?q=" + query;
		if (advanced) {
			requestUrl += "&qt=advanced";
		} else {
			requestUrl += "&qt=term";
		}

		log.info("querying sindice: " + requestUrl);
		String content = UrlUtils.readUrlAsString(requestUrl);

		return content;
	}

}
