package crawler.festival;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import crawler.utils.Utils;

public class Festival implements Runnable{

	@Override
	public void run() {
		
		try {
			
		Hashtable<Integer, ArrayList<String>> figureNameAndId = Utils.getSimpleHistoricalFigureList();
				
		String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam";
        Document doc = null;

        doc = Jsoup.connect(url)
                   .userAgent("Jsoup client")
                   .timeout(20000).get();
            
        Element table = doc.select("table").get(1); 
        Elements rows = table.select("tr");

        JSONArray festivalList = (JSONArray)new JSONArray();
            
        for (int i = 1; i < rows.size(); i++) { // first row is the column names so skip it.
	            	
	        Element row = rows.get(i);
	            	
	        Element date = row.select("td").get(0);            	
	        	        
	        Element place = row.select("td").get(1);            	
	        		
	        Element name = row.select("td").get(2);            	
	        		
	        Element firstHeld = row.select("td").get(3);
	        		
	        Element relatedCharacters = row.select("td").get(4);
	        		
	        String relatedCharacterList = relatedCharacters.text();
	        		
			ArrayList<Integer> relatedFiguresId = new ArrayList<Integer>();
					
			String[] relatedFigureNames =relatedCharacterList.split(", ");
					
			for (int k = 0; k < relatedFigureNames.length ; k ++) {
				
				String figureName = relatedFigureNames[k];
				
				figureNameAndId.forEach((figureId, nameList) -> {
					
					for (String individualName : nameList) {
						
						if ((individualName.toLowerCase().equals(figureName.toLowerCase())) && individualName.length() >= 2) {
							relatedFiguresId.add(figureId);
							break;
						}
						
					}
				});
				
				// If figure id can't be found, use 0 instead 
				if (relatedFiguresId.size() == k) {
					relatedFiguresId.add(0);
				}
			}
			
			
	        JSONObject festival =  (JSONObject)new JSONObject();
	        
	        festival.put("id", i);
	        festival.put("lunarDate", date.text());
	        festival.put("place", place.text());
	        festival.put("name", name.text());
	        festival.put("firstHeld", firstHeld.text());
	        festival.put("relatedCharacters", relatedCharacters.text());
	        festival.put("relatedFiguresId", relatedFiguresId);

	        festivalList.put(festival);    
        }
         
	        File file = new File("src\\data\\festival.json");
	        file.getParentFile().mkdirs();
	        FileWriter myWriter = new FileWriter(file);

            myWriter.write(festivalList.toString());
            
            myWriter.close();
		} catch (IOException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}
