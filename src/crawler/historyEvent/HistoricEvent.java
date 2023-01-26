package crawler.historyEvent;

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

public class HistoricEvent {
	
    public static JSONObject infoFromLink(String url) throws IOException, JSONException {
    	
    	JSONObject historicEvent = new JSONObject();
    	
    		Document doc = Jsoup
    				.connect(url)
    				.userAgent("Jsoup client")
    				.timeout(20000).get();
    		
    		
    		Elements listElement = doc.select(".divide-tag");
    		
    		Element name = listElement.get(0).selectFirst(".header-edge");
    		historicEvent.put("name", name.text());
    		
    		for(int i = 1; i < listElement.size(); i++) {
    			
    			if(i == 1) {
    				Elements detail = listElement.get(i).select(".card-body");
    				
    				historicEvent.put("detail", detail.get(0).text());
    				
    			} else if(i == 2){
    				Elements referenceLinks = listElement.get(i).select(".card-body>a.nut_nhan");
    				
    				String allReferenceUrls = "";
    				
    				for (int j = 0; j < referenceLinks.size(); j ++) {
    					allReferenceUrls += referenceLinks.get(j).text() + "\n";
    				}
    				
    				historicEvent.put("references",allReferenceUrls);
    			} else {
    				
    				Elements key = listElement.get(i).select("h3.header-edge");
    				Elements values = listElement.get(i).select(".card-body>a.click");
    				
    				if (key.get(0).text().equals("Nhân vật liên quan")) {
    					
    					String relatedFigures = "";
    					    					
    					for (int j = 0; j < values.size(); j ++) {
    						if (j < values.size() - 1) {    							
    							relatedFigures += values.get(j).text() + ", ";
    						}
    						
    						if (j == values.size() - 1) {
    							relatedFigures += values.get(j).text();
    						}
    					}
    					
    					historicEvent.put("relatedFigures", relatedFigures);
    				} else {
    					String relatedSites = "";
    					
    					for (int j = 0; j < values.size(); j ++) {
    						if (j < values.size() - 1) {    							
    							relatedSites += values.get(j).text() + ", ";
    						}
    						
    						if (j == values.size() - 1) {
    							relatedSites += values.get(j).text();
    						}
    					}
    					
    					historicEvent.put("relatedSites", relatedSites);    				}
    			}
    		}
    		
    	return historicEvent;
	}

	public static void main(String[] args) throws JSONException {
		
		try {
			JSONArray historicEventList = new JSONArray();
			
			File historicEventUrlsFile = new File("historicEventUrls.txt");
		    Scanner myReader = new Scanner(historicEventUrlsFile);
		    
		    while (myReader.hasNextLine()) {
		    	
		        String link = myReader.nextLine();
		        historicEventList.put(infoFromLink(link));
		     }
		    
	        myReader.close();
	        System.out.print(historicEventList.toString());
	        FileWriter file = new FileWriter("history_event.json");
            file.write(historicEventList.toString());
            
            file.close();
	        
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
