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
package test;

import static org.junit.Assert.*;
import libswb.LibSwb;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sven Buschbeck
 *
 */
public class TestMergingFoafProfiles {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		/*System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "proxy.unitn.it");
		System.getProperties().put("proxyPort", "3128");*/
	}

	/**
	 * Test method for {@link libswb.LibSwb_#gotoUri(java.lang.String)}.
	 */
	@Test
	public void testChangePivotUri() {
		System.out.println("testChangePivotUri...");
		
		long startTime = System.currentTimeMillis();
		LibSwb libswb = new LibSwb();
		libswb.gotoUri("http://www.hs-augsburg.de/~bucks/foaf.rdf");
		
		long initTime = System.currentTimeMillis() - startTime;
		System.out.println("init time: "+initTime);
		libswb.gotoUri("http://www.okkam.org/entity/ok42fe5511-c177-435a-8cf7-18b6a881d8b7");
		
		long changeTime = System.currentTimeMillis() - startTime;
		System.out.println("change time: "+changeTime);
		libswb.printGraph();
		System.out.println("total time: "+(System.currentTimeMillis() - startTime));
	}

	/**
	 * Test method for {@link libswb.LibSwb_#init(java.lang.String)}.
	 */
	@Test
	public void testInit() {
		System.out.println("\n\ntestInit...");
		
		long startTime = System.currentTimeMillis();
		LibSwb libswb = new LibSwb();
		libswb.gotoUri("http://www.hs-augsburg.de/~bucks/foaf.rdf");
		libswb.printGraph();
		System.out.println("total time: "+(System.currentTimeMillis() - startTime));
	}

}
