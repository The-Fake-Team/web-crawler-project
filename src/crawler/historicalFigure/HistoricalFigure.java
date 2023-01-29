package crawler.historicalFigure;

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

public class HistoricalFigure {
	
	private static int figureId = 1;
	
	public static JSONObject infoFromLink(String url) throws IOException, JSONException {
		
		Document doc = Jsoup
				        .connect(url)
				        .userAgent("Jsoup client")
				        .timeout(20000).get();
		String[] allTimeStamps = {"2000 - 258", "257- 208", "207 trCN - 39", "40-43", "43-542",
				 "544-602", "603-939", "905 - 938", "939-965", "968-980", "980-1009",
				 "1010-1225", "1225-1400", "1400-1407", "1407-1413", "1414-1427", "1428-1527",
				 "1527-1592", "1533-1788", "1778-1802", "1802-1883", "1883-1945", "Nước Việt Nam mới"};

	    int[][] allNormalizedTimeStamps = {{-2000, -258}, {-257, -208}, {-207, -39}, {40, 43}, {43, 542},
						 {544, 602}, {603, 939}, {905, 938}, {939, 965}, {968, 980}, {980, 1009},
						 {1010, 1225}, {1225, 1400}, {1400, 1407}, {1407, 1413}, {1414, 1427}, {1428, 1527},
						 {1527, 1592}, {1533, 1788}, {1778, 1802}, {1802, 1883}, {1883, 1945}, {1945, 2023}};
	    
    	JSONObject historicalfigure = new JSONObject();
    	historicalfigure.put("id", figureId);
    	figureId++;
        		
		Element name = doc.select("div.active.section").first();
		historicalfigure.put("name", name.text());
		
		Element table = doc.select("table").get(0); // select main table of current page
		Elements rows = table.select("tr");
		
		
		
		for (int i = 0; i < rows.size(); i++) {
			
			Element row = rows.get(i);
	
			if (i < rows.size() - 1) {
				Element key = row.select("td").get(0);
				Element value = row.select("td").get(1);
				
				if (key.text().equals("Thời kì")) {
					JSONArray eras = new JSONArray();
					String eraSummary = value.text();
					String[] eraList = eraSummary.substring(1).trim().split("\\) - ");
					
					for (int j = 0; j < eraList.length; j ++) {
						JSONObject era = new JSONObject();
						
						era.put("name", eraList[j].replaceAll("\\(.+", "").trim());
						
						for (int k = 0; k < allTimeStamps.length; k++) {
			        		
			        		if (eraSummary.contains(allTimeStamps[k])) {
								era.put("start", allNormalizedTimeStamps[k][0]);
								era.put("end", allNormalizedTimeStamps[k][1]);
			        		}
			        	}
						eras.put(era);
						historicalfigure.put("eras", eras );
					}
				}
				
				if (key.text().equals("Tỉnh thành")) {
					historicalfigure.put("place", value.text());
				}
				
				if (key.text().equals("Tên khác")) {
					historicalfigure.put("otherName", value.text());
				}
				
				if (key.text().equals("Năm sinh")) {
					historicalfigure.put("yearOfBirth", value.text().trim()); // remove "-"
				}
				
				
				// check for empty fields
				
				if (!historicalfigure.has("yearOfBirth")) {
					historicalfigure.put("yearOfBirth", "");
				}
				
				if (!historicalfigure.has("otherName")) {
					historicalfigure.put("otherName", "");
				}
				
			};
			
			if (i == rows.size() - 1) {
				Element descripiton = row.select("td").get(0);
				
				historicalfigure.put("descripiton", descripiton.text());
			}
		}
		
		return historicalfigure;		
	}

	public static void main(String[] args) throws JSONException {
		
		try {
			
			JSONArray figureList = new JSONArray();
			
			File myFile = new File("historicalFigureUrls.txt");
		    Scanner myReader = new Scanner(myFile);
		    
		    while (myReader.hasNextLine()) {
		        String url = myReader.nextLine();
		        
		        figureList.put(infoFromLink(url));
		     }
		    
	        myReader.close();
	        
	        FileWriter file = new FileWriter("historicalFigureWithId.json");
            file.write(figureList.toString());
            
            file.close();
	        
	    } catch (IOException e) {
	    	
	    	e.printStackTrace();
	    }
	}

}
