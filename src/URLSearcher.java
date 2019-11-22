import java.net.*;
import java.io.*;
import java.util.regex.*;

public class URLSearcher extends Thread {

	private static URLIndex index;
	private static int nbResults = 0;
	private String baseURL;
	private String word;
	private int maxResults;

	public URLSearcher(String baseURL, String word, int maxResults) {
		if (index == null)
			index = new URLIndex();
		this.baseURL = baseURL;
		this.word = word;
		this.maxResults = maxResults;
	}

	@Override
	public void run() {
		this.search();
	}

	public void search() {
		String mainUrl = baseURL + word;
		String html = this.getHtml(mainUrl);

		final String REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
		Pattern patternHttp = Pattern.compile(REGEX);

		final String REGEX2 = "<a[^>]* href=\"([^\"]*)\"";
		Pattern patternHref = Pattern.compile(REGEX2);
		Matcher matcherHref = patternHref.matcher(html);

		String domainName;

		try {
			domainName = getDomain(mainUrl);

			while (matcherHref.find() && nbResults <= maxResults) {
				String url = matcherHref.group(1);

				if (url.contains(word) && !index.isInIndex(url)) {
					Matcher matcherHttp = patternHttp.matcher(url);
					if (!matcherHttp.find()) {
						System.out.println(nbResults + ") " + domainName + url);
					} else {
						System.out.println(nbResults + ") " + url);
					}
					nbResults++;
				}
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static String getDomain(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String dom = uri.getHost();
		return dom.startsWith("www.") ? dom.substring(4) : dom;
	}

	public String getHtml(String url) {
		String content = "";
		try {
			URL urlObject = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlObject.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				content += inputLine;
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return content;
	}

	public int getNbResults() {
		return nbResults;
	}

}
