package crawler.utils;

import org.json.JSONException;
import org.json.JSONObject;
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
	
	 public static void putToObject(JSONObject object, Elements keys, Elements values) {

	        /// Because the number of value can be more than the number of key
	        /// The number of values is more than the keys because the last key "reign" has 3 values as "start reigning year
	        /// ", "-", "end reigning year"
	    	
	        String lastValue = "";
	        int lastKeyIndex = keys.size() - 1;
	        
	        if (values.size() >= keys.size()) {
	            for (int j = lastKeyIndex; j < values.size(); j++) {
	                lastValue = lastValue.concat(
	                        values.get(j).ownText()
	                                .replaceAll("\\[([^\\]]+)\\]", ",")
	                                .replaceAll("\\(([^\\]]+)\\)", ",")
	                                .trim());
	            }
	        }

	        try {
	            for (int i = 0; i < lastKeyIndex; i++) {
	                String key = keys.get(i).wholeOwnText()
	                        .replace("\n", "")
	                        .trim();
	                String value = values.get(i).wholeText()
	                        .replaceAll("\\[([^\\]]+)\\]", ",")
	                        .replaceAll("\\(([^\\]]+)\\)", ",")
	                        .trim();
	                object.put(key, value);
	            }
	            object.put(
	                    keys.get(lastKeyIndex).wholeOwnText().replace("\n", "").trim(), lastValue);
	        } catch (JSONException je) {
	            je.printStackTrace();
	        }
	    }

}
