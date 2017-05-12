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


import java.util.HashMap;
import java.util.HashSet;

import libswb.Queue;
import libswb.ResourceNode;
import libswb.graph.Graph;
import libswb.graph.GraphFactory;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author Sven Buschbeck
 * 
 */
public class Production {

	
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * @param graph
	 * @param i
	 */
	public Graph prune(Graph graph, String pivotURI, int maxLevel) {
		// TODO Auto-generated method stub
		// delete triples with level > maxLevel
		long start = System.currentTimeMillis();
		log.debug("prunning: start Graph size: "+graph.getModel().size());
		Model pruned =ModelFactory.createDefaultModel();
		Model startModel =graph.getModel();
		Resource pivot = startModel.getResource(pivotURI);
		// retrieve nodes within the threshold level
		HashMap<String,ResourceNode> nodeSet = BFSubgraphSelection(graph,  pivot, maxLevel);
		// build the union of all the subgraph of the node within the threshold
		//System.out.println("explored nodes: "+nodeSet.size());
		
		pruned =startModel.query(new SimpleSelector(pivot,null,(RDFNode)null));
		
		int c=0;
		for(ResourceNode node:nodeSet.values()) {
			//System.out.println(c++);
			Model prunedTmp = pruned.union(startModel.query(new SimpleSelector((node.getResource()),null,(RDFNode)null)));
			pruned = prunedTmp;
			
		}
		
		long end = System.currentTimeMillis();
		log.debug(" end size: "+pruned.size()+" |  time: "+ (end-start));
		
		return GraphFactory.get().getGraph(pruned);
	}

	private HashMap<String,ResourceNode> BFSubgraphSelection(Graph graph, Resource pivot, int maxLevel) {
		Queue q = new Queue();
		HashMap<String, ResourceNode> visited = new HashMap<String,ResourceNode>();
		Model model = graph.getModel();
		// start iteration level
		int level = 0;
		// queue initialize
		q.enqueue(new ResourceNode(pivot, level));
		//System.out.println("first node added in the queue: "+q.size());
		// queue iteration
		
		while (q.hasItems()) {
			//System.out.println("queue size: "+q.size());
			// dequeue visited ResourceNode
			ResourceNode node = q.dequeue();
			// check whether visited node is within the threshold level
			if (node.getLevel() < maxLevel) {
				// retrieve node that are close
				Model modelTmp = model.query(new SimpleSelector(node
						.getResource(), null, (RDFNode) null));
				Model modelTmp2 = model.query(new SimpleSelector(null, null,node
						.getResource()));
				Model modelTmp3 = modelTmp2.union(modelTmp);
				StmtIterator iter = modelTmp3.listStatements();
				while (iter.hasNext()) {
					Statement stmt = iter.nextStatement();
					RDFNode res = stmt.getObject();
					RDFNode sub = stmt.getSubject();
					// check if the node is a resource or already visited
					if (res.isResource() && !visited.containsKey(((Resource)res).getURI())) {
						ResourceNode visitedNode = new ResourceNode(
								(Resource) res, (node.getLevel())+1);
						// enqueue visited node
						q.enqueue(visitedNode);
						// add node to visited
						visited.put(((Resource)res).getURI(),visitedNode);
						//System.out.println("adding visited node: "+visited.size() +" with depth: "+visitedNode.getLevel());
					}
					if (sub.isResource() && !visited.containsKey(((Resource)sub).getURI())) {
						ResourceNode visitedNode = new ResourceNode(
								(Resource) sub, (node.getLevel())+1);
						// enqueue visited node
						q.enqueue(visitedNode);
						// add node to visited
						visited.put(((Resource)sub).getURI(),visitedNode);
						//System.out.println("adding visited node: "+visited.size() +" with depth: "+visitedNode.getLevel());
					}
				}
				
			} else {
				// if level of node is above the threshold level, return the list of visited nodes
				return visited;
			}
		}
		return visited;
	}

}
