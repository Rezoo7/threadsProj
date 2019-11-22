
public class Main {

    public static void main(String[] args){

        URLSearcher searcher = new URLSearcher();
        Thread thread = new Thread(searcher);

        thread.start();
    }

}
