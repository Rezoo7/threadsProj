import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.regex.*;


public class Main {

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

    public String run(String city){
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

            while (matcherHref.find()) {
                String url = matcherHref.group(1);

                if (url.contains(word)) {
                    Matcher matcherHttp = patternHttp.matcher(url);
                    if (!matcherHttp.find()) {
                        urls.add(domainName + url);
                    } else {
                        urls.add(url);
                    }
                }
            }

            int count = 0;
            for (String url : urls) {
                System.out.println(url);
                count++;
            }

            System.out.println(count);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){

        Main m = new Main();
        m.run("Nantes");

    }
}
