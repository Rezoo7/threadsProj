import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;


public class URLSearcher extends Thread {

    @Override
    public void run() {
      this.search("Nantes");
    }
	
    public ArrayList<String> search(String city){
        String mainUrl = "https://fr.wikipedia.org/wiki/"+city;
        String html = this.getHtml(mainUrl);

        final String REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
        Pattern patternHttp = Pattern.compile(REGEX);

        final String REGEX2 = "<a[^>]* href=\"([^\"]*)\"";
        Pattern patternHref = Pattern.compile(REGEX2);
        Matcher matcherHref = patternHref.matcher(html);

        ArrayList<String> urls = new ArrayList<String>();
        String domainName;
        String word = city;

        try {
            domainName = getDomain(mainUrl);
            int count = 0;


            while (matcherHref.find()) {
                String url = matcherHref.group(1);

                if (url.contains(word)) {
                    Matcher matcherHttp = patternHttp.matcher(url);
                    if (!matcherHttp.find()) {
                        urls.add(domainName + url);
                        System.out.println(url);
                        sleep(200);
                        count++;
                    } else {
                        urls.add(url);
                        System.out.println(url);
                        sleep(200);
                        count++;
                    }
                }
            }

            System.out.println("Nombre de résultats : " + count);
            return urls;

        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
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
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }

        return content;
    }

}
