public class Main {

	private static final String BASE_URL = "https://fr.wikipedia.org/wiki/";
	private static final String SEARCHED_WORD = "Nantes";
	private static final int MAX_RESULTS = 1000;
	private static final int NB_THREADS = 10;
	
    public static void main(String[] args){
    	
    	for (int i = 0; i < NB_THREADS; i++) {
    	    String a = args[1];
    	    String b = args[2];

    	    if (a.equals("launcher.sh")){
                URLSearcher searcher = new URLSearcher(BASE_URL, args[2], MAX_RESULTS);
                Thread thread = new Thread(searcher);
                thread.start();
            }else{
                URLSearcher searcher = new URLSearcher(BASE_URL, args[1], MAX_RESULTS);
                Thread thread = new Thread(searcher);
                thread.start();
            }
    	}
        
    }
    
    /*
    public ArrayList<String> search(String city){
        String mainUrl = baseURL + word;
        String html = this.getHtml(mainUrl);

        final String REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
        Pattern patternHttp = Pattern.compile(REGEX);

        final String REGEX2 = "<a[^>]* href=\"([^\"]*)\"";
        Pattern patternHref = Pattern.compile(REGEX2);
        Matcher matcherHref = patternHref.matcher(html);

        ArrayList<String> urls = new ArrayList<String>();
        String domainName;

        try {
            domainName = getDomain(mainUrl);
            int count = 0;


            while (matcherHref.find()) {
                String url = matcherHref.group(1);

                if (url.contains(city)) {
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
    }*/
     

}
