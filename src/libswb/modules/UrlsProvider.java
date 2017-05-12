package libswb.modules;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.okkam.util.registry.*;

public class UrlsProvider {
	
	private Logger log = Logger.getLogger(this.getClass());
	private GenericClassRegistry<UrlsProviderModule> classRegistry;

	public UrlsProvider() {
		classRegistry = GenericClassRegistry.create(UrlsProviderModule.class);
	}
	
	public List<String> UrlsForUri(String uri) {
		Set<String> urls = new TreeSet<String>();
		for (UrlsProviderModule module : classRegistry.getInstances()) {
			urls.addAll(module.retrieveUrls(uri));
		}
		return new LinkedList<String>(urls);
	}
}
