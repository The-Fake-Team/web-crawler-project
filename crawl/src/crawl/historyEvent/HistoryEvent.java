import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HistoryEvent {
    public static JSONObject infoFromLink(String link) throws IOException, JSONException {
		Document doc = Jsoup
				        .connect(link)
				        .userAgent("Jsoup client")
				        .timeout(50000).get();
		
    	JSONObject historyEvent = new JSONObject();
        
		Elements listElement = doc.select(".divide-tag");
        Elements name = listElement.get(0).select(".header-edge");
        historyEvent.put("name", name.get(0).text());
		for(int i = 1; i < listElement.size(); i++) {
            if(i == 1) {
                Elements key = listElement.get(i).select("h3.header-edge");
                Elements value = listElement.get(i).select(".card-body");
                historyEvent.put(key.get(0).text(), value.get(0).text());
            } else if(i !=2 ){
				Elements key = listElement.get(i).select("h3.header-edge");
                Elements value = listElement.get(i).select(".card-body>a.click ");
				historyEvent.put(key.get(0).text(), value.get(0).text());
            }
        }
		return historyEvent;		
	}

	public static void main(String[] args) throws JSONException {
		try {
			JSONArray listHistory = new JSONArray();
			
			File myObj = new File("history_event_page.txt");
		    Scanner myReader = new Scanner(myObj);
		    while (myReader.hasNextLine()) {
		        String link = myReader.nextLine();
                System.out.println(link);
		        listHistory.put(infoFromLink(link));
		     }
	        myReader.close();
	        
	        FileWriter file = new FileWriter("history_event.json");
            file.write(listHistory.toString());
            
            file.close();
	        
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
