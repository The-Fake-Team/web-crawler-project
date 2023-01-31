package crawler.historyEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.utils.Utils;

public class HistoricEvent implements Runnable{
	
	private static int eventId = 1;
	
	@SuppressWarnings("unchecked")
	public static JSONObject createEraObject(ArrayList<Integer> startEndYearArray) {
		JSONObject time = new JSONObject();
		
		time.put("start", startEndYearArray.get(0));
		time.put("end", startEndYearArray.get(1));
		
		return time;
		
	}
	
    @SuppressWarnings("unchecked")
	public static JSONObject infoFromLink(String url, Hashtable<Integer, ArrayList<String>> figureNameAndId) throws IOException, JSONException {
    	
    	JSONObject historicEvent = new JSONObject();
    	
    	historicEvent.put("id", eventId);
    	eventId++;
    	
		Document doc = Jsoup
				.connect(url)
				.userAgent("Jsoup client")
				.timeout(20000).get();
		
		
		Elements listElement = doc.select(".divide-tag");
		
		Element name = listElement.get(0).selectFirst(".header-edge");
		historicEvent.put("name", name.text());

				
			// Extract the years from the event name
			
		ArrayList<Integer> erasYears = new ArrayList<Integer>();
			
		Pattern p = Pattern.compile("\\(-?[\\d\\s].*\\)");
		Matcher m = p.matcher(name.text());
			
		if (m.find()) {	
			
			String[] startEndTimeArray = m.group(0).replaceAll("\\(", "").replaceAll("\\)", "").split(" - ");
				
			if (startEndTimeArray.length == 2) {
					
				String startTime = startEndTimeArray[0].trim();
				String endTime = startEndTimeArray[1].trim();
					
				if (startTime.length() > 0) {
					erasYears.add(Integer.parseInt(startTime));
				} else {
					erasYears.add(null);
				}
					
				if (endTime.length() > 0) {
					erasYears.add(Integer.parseInt(endTime));
				} else {
					erasYears.add(null);
				}
				
				} else {
					
					if (startEndTimeArray[0].trim().length() > 0) {						
						erasYears.add(Integer.parseInt(startEndTimeArray[0].trim()));
						erasYears.add(Integer.parseInt(startEndTimeArray[0].trim()));
					} else {
						erasYears.add(null);
						erasYears.add(null);
					}
				}
			}
			
			historicEvent.put("time", createEraObject(erasYears));
		
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
					String relatedPlaces = "";
					
					for (int j = 0; j < values.size(); j ++) {
						if (j < values.size() - 1) {    							
							relatedPlaces += values.get(j).text() + ", ";
						}
						
						if (j == values.size() - 1) {
							relatedPlaces += values.get(j).text();
						}
					}
					
					historicEvent.put("relatedPlaces", relatedPlaces);    				
				}
			}
		}
    		
    	return historicEvent;
	}

    @SuppressWarnings("unchecked")
	public void run() {
		
    	try {
    		Hashtable<Integer, ArrayList<String>> figureNameAndId = Utils.getSimpleHistoricalFigureList();
    	
			JSONArray historicEventList = new JSONArray();

			File historicEventUrlsFile = new File("src\\crawler\\historyEvent\\historicEventUrls.txt");
		    Scanner myReader = new Scanner(historicEventUrlsFile);
		    
		    while (myReader.hasNextLine()) {
		    	
		        String url = myReader.nextLine();
		        try {
		        	historicEventList.add(infoFromLink(url, figureNameAndId));
		        }
		        catch (IOException e) {
		        	System.out.println(url + " has an error");
		        	continue;
		        }
		     }
		    
	        myReader.close();

	        File file = new File("src\\data\\historicalEvent.json");
            file.getParentFile().mkdirs();
            FileWriter myWriter = new FileWriter(file);
            
	        myWriter.write(historicEventList.toString());
            
	        myWriter.close();
            
    	} catch (JSONException | ParseException| IOException e) {
    		e.printStackTrace();
    	}
	        
	}
}
