import java.util.ArrayList;

public class URLIndex {

	private ArrayList<String> checkedURL;
	
	public URLIndex() {
		checkedURL = new ArrayList<>();
	}
	
	public synchronized boolean isInIndex(String url) {
		return checkedURL.contains(url);
	}
	
	public synchronized void addCheckedURL(String url) { 
		checkedURL.add(url);
	}
	
	public ArrayList<String> getCheckedURL() {
		return checkedURL;
	}
	
}
