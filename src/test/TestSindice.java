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
import libswb.modules.Sindice;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sven Buschbeck
 *
 */
public class TestSindice {

	private Sindice sindice;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", "proxy.unitn.it");
		System.getProperties().put("proxyPort", "3128"); 
		sindice = new Sindice();
	}

	/**
	 * Test method for {@link libswb.modules.Sindice#query(java.lang.String)}.
	 */
	@Test
	public void testQuery() {
		System.out.println(sindice.retrieveUrls("test"));
	}

}
