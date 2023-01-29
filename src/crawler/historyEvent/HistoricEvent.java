package crawler.historyEvent;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.utils.Utils;

public class HistoricEvent {
	
	private static Hashtable<Integer, ArrayList<String>> figureNameAndId = new Hashtable<Integer, ArrayList<String>>();
	private static int eventId = 1;
	
    @SuppressWarnings("unchecked")
	public static JSONObject infoFromLink(String url) throws IOException, JSONException {
    	
    	JSONObject historicEvent = new JSONObject();
    	
    	historicEvent.put("id", eventId);
    	eventId++;
    	
		Document doc = Jsoup
				.connect(url)
				.userAgent("Jsoup client")
				.timeout(50000).get();
		
		
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
					ArrayList<Integer> relatedFiguresId = new ArrayList<Integer>();
					    					
					for (int j = 0; j < values.size(); j++) {
						String figureName = values.get(j).text().replaceAll("\\(.*?\\) ?", "").trim();
						
						figureNameAndId.forEach((figureId, nameList) -> {
							
							for (String eachName : nameList) {
								// add reference figure id
								if ((eachName.toLowerCase().equals(figureName.toLowerCase())) && eachName.length() >= 2) {
									relatedFiguresId.add(figureId);
									break;
								}
								
							}
						});
						
						// If figure id can't be found, use 0 instead 
						if (relatedFiguresId.size() == j) {
							relatedFiguresId.add(0);
						}
						
						if (j < values.size() - 1) {    							
							relatedFigures += figureName + ", ";
						}
						
						if (j == values.size() - 1) {
							relatedFigures += figureName;
						}
					}
					
					historicEvent.put("relatedFigures", relatedFigures);
					historicEvent.put("relatedFiguresId", relatedFiguresId);
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
					
					historicEvent.put("relatedSites", relatedSites);    				
				}
			}
		}
    		
    	return historicEvent;
	}

    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws JSONException, ParseException {
		
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("historicalFigureWithId.json")) {
			
			Object obj = jsonParser.parse(reader);			
			JSONArray figureList = (JSONArray) obj;

			for (int i = 0; i < figureList.size(); i++) {
				JSONObject figure = (JSONObject) figureList.get(i);
				int id = ((Long) figure.get("id")).intValue();
				figureNameAndId.put(id, new ArrayList<String>());
				figureNameAndId.get(id).add((String) figure.get("name"));
				figureNameAndId.get(id).add((String) figure.get("otherName"));
			}
			
			JSONArray historicEventList = new JSONArray();

			File historicEventUrlsFile = new File("historicEventUrls.txt");
		    Scanner myReader = new Scanner(historicEventUrlsFile);
		    
		    while (myReader.hasNextLine()) {
		    	
		        String link = myReader.nextLine();
		        try {
		        	historicEventList.add(infoFromLink(link));
		        }
		        catch (IOException e) {
		        	System.out.println(link + " has an error");
		        	continue;
		        }
		     }
		    
	        myReader.close();

	        FileWriter file = new FileWriter("historicalEvent.json");
            file.write(historicEventList.toString());
            
            file.close();
	        
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
