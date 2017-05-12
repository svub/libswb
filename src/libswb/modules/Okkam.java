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

import java.util.ArrayList;
import java.util.List;

import libswb.LibswbUtils;

import org.okkam.client.wsclient.EntityManagerClient;
import org.okkam.core.data.api.MatchingCandidate;
import org.okkam.core.data.api.MatchingCandidates;
import org.okkam.core.data.entity.ReferencesType;
import org.okkam.core.data.entity.ReferenceType;
import org.okkam.core.data.entity.Entity;
import org.okkam.core.ws.data.ClientMetadata;
import org.okkam.core.ws.data.ClientResult;

/**
 * @author Sven Buschbeck
 * 
 */
public class Okkam {

	private final ClientMetadata clientMetadata = null;
	private final EntityManagerClient entityManager = new EntityManagerClient();

	private String okkamId;
	private MatchingCandidate[] okkam_entities;

	/**
	 * @param keywords
	 */
	public String query(String keywords) {
		// TODO Auto-generated method stub
		// query okkam with keywords provided and return the okkam identifier
		MatchingCandidates queryResult = null;
		ClientResult clientResult = null;

		try {
			long start = System.currentTimeMillis();
			clientResult = entityManager.getEntity(keywords, clientMetadata,
					"asdfghjkl");
			System.out.println("Query Time: "
					+ (System.currentTimeMillis() - start) + " ms");
		} catch (Exception e) {
			// error one management
			e.printStackTrace();
		}
		
		// if result is not null, return the proper signal
		if (clientResult != null && clientResult.getResults().length != 0) {

			System.out.println("result not null! "
					+ clientResult.getResults().length + " ");
			//if okkam returns only one result, save the 
			if (clientResult.getResults().length == 1) {
				for (MatchingCandidate candidate : clientResult.getResults()) {
					setOkkamId(candidate.getOid());
					return LibswbUtils.ONE_ENTITY_MATCHING;
				}
			} else {
				okkam_entities = clientResult.getResults();
				return LibswbUtils.SEVERAL_OKKAM_ENTITIES_RETURNED;
			}

		} else {
			if (queryResult == null) {
				System.out.println("QUERY RESULT NULL: ");
				return LibswbUtils.NO_OKKAM_ENTITY_RETURNED;
			} else {
				System.out.println("NO ENTITY RETURNED");
				return LibswbUtils.NO_OKKAM_ENTITY_RETURNED;
			}
			// if okkam does not contain any element

		}
		return null;

	}

	/**
	 * @param okkamid
	 */
	public List getAlternativeIds(String okkamid) {
		// inquire okkam for alternative ids of entity
		return entityManager.getAlternativeIds(okkamid, clientMetadata,
		"asdfghjkl");
	}

	/**
	 * @param okkamid
	 */
	public List getWebReferences(String okkamid) {
		// inquire okkam for web references
		ArrayList<String> references = new ArrayList<String>();
		for(ReferenceType ref: entityManager.fetchEntity(okkamid, clientMetadata, "asdfghjkl").getProfile().getReferences().getReferences()) {
			
			references.add(ref.getPointer());
		}
		
		return references;
	}
	
	/**
	 * @param threshold value
	 */
	public List<MatchingCandidate> getEntityList(double threshold){
		List<MatchingCandidate> filteredList = new ArrayList<MatchingCandidate>();
		
		for(MatchingCandidate entity : okkam_entities) {
			if(entity.getSim()>=threshold) {
				filteredList.add(entity);
			}
		}
		
		return filteredList;
	}
	
	
	public String getOkkamId() {
		return okkamId;
	}

	public void setOkkamId(String okkamId) {
		this.okkamId = okkamId;
	}

}