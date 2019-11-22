import java.util.ArrayList;

public class URLIndex {

	private ArrayList<String> checkedURL;
	
	public URLIndex() {
		checkedURL = new ArrayList<>();
	}
	
	public void addCheckedURL(String url) {
		checkedURL.add(url);
	}
	
	public ArrayList<String> getCheckedURL() {
		return checkedURL;
	}
	
}
