package libswb;

import com.hp.hpl.jena.rdf.model.Resource;

public class ResourceNode {
	
	private Resource resource;
	private int level;
	
	public ResourceNode(Resource res, int lev) {
		this.resource = res;
		this.level = lev;
	}
	
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

}
