package crawler.utils;


import java.util.ArrayList;

import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.select.Elements;

public final class Utils {
	
	public Utils() {
	    
	}	
	
	public static String removeTitle(String name) {
		
		// Remove title of historical figures, such as "vua", "chúa",... 
		String[] titlesList = {"vua", "chúa", "đại tướng", "chủ tịch", "thủ tướng"};
		for (String title : titlesList) {
			if (name.startsWith(title)) {
				name = name.replace(title, "").trim();
				break;
			}
		}
		return name;
	}
	
	public static String extractInt(String str)
    {
        // Replacing every non-digit number
        // with a space(" ")
        str = str.replaceAll("[^\\d]", " ");
 
        // Remove extra spaces from the beginning
        // and the ending of the string
        str = str.trim();
 
        // Replace all the consecutive white
        // spaces with a single space
        str = str.replaceAll(" + ", " ");
 
        if (str.equals(" "))
            return "-1";
        return str;
    }
	
	 public static Hashtable<Integer, ArrayList<String>> getSimpleHistoricalFigureList () throws IOException, ParseException {
		 
		    Hashtable<Integer, ArrayList<String>> figureNameAndId = new Hashtable<Integer, ArrayList<String>>();
			
			JSONParser jsonParser = new JSONParser();
			FileReader myReader = new FileReader("src\\data\\historicalFigure.json");
			
			Object obj = jsonParser.parse(myReader);	
			
			JSONArray figureList = (JSONArray) obj;
			
			for (int i = 0; i < figureList.size(); i++) {
				
				JSONObject figure = (JSONObject) figureList.get(i);
				int id = ((Long) figure.get("id")).intValue();
				figureNameAndId.put(id, new ArrayList<String>());
				figureNameAndId.get(id).add((String) figure.get("name"));
				figureNameAndId.get(id).add((String) figure.get("otherName"));
			}
			
			return figureNameAndId;
	 }

}
