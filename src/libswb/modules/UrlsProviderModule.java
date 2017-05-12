package libswb.modules;

import java.net.URL;
import java.util.List;

public interface UrlsProviderModule {
	
	public List<String> retrieveUrls(String uri);
}
