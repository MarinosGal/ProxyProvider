package proxy.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import proxy.provider.bean.Proxy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a Proxy that is always available and valid
 * @author Marinos Galiatsatos
 * @since 11 April 2020
 */
public class ProxyProvider {

	public static final Logger logger = Logger.getLogger(ProxyProvider.class.getName());

	private String userAgent;
	private String proxySite;
	private boolean ignoreHttpErrors;
	private Iterator<Proxy> proxyIterator;

	public ProxyProvider() {
		this(true);
	}

	/**
	 * The ProxyProvider's constructor initializes the agent and the default site to crawl the proxies
	 * @param ignoreHttpErrors whether or not to ignore any http errors
	 * @link https://free-proxy-list.net/
	 */
	public ProxyProvider(boolean ignoreHttpErrors){
		this.userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
		this.proxySite = "https://free-proxy-list.net/";
		this.ignoreHttpErrors = ignoreHttpErrors;
		findProxies();
	}

	/**
	 * Returns an always available and valid proxy
	 * @return a Proxy object with an ip and a port
	 */
	public Proxy getProxy() {
		if (!proxyIterator.hasNext()) {
			findProxies();
		}
		return proxyIterator.next();
	}

	private void findProxies(){
		try {
			logger.log(Level.INFO, "Finding available proxies...");
			Connection connection = Jsoup.connect(proxySite).userAgent(userAgent);
			connection.ignoreHttpErrors(ignoreHttpErrors);
			String html = connection.get().html();
			Document doc = Jsoup.parse(html, "utf-8");
			Element table = doc.getElementById("list");
			Elements trs = table.select("tr");

			List<Proxy> proxyList = new ArrayList<>();
			for(Element element: trs) {
				Elements elems = element.select("td");
				if (elems.size() > 0) {
					proxyList.add(new Proxy(elems.get(0).text(), Integer.parseInt(elems.get(1).text())));
				}
			}
			logger.log(Level.INFO, "Found {"+proxyList.size()+"} proxies.");
			proxyIterator = proxyList.iterator();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error when trying to find proxies - "+e.toString());
		}
	}

}
