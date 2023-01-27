package crawler.festival;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Festival {

	public static void main(String[] args) throws JSONException {
		String url = "https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam";
        Document doc = null;

        try {
            doc = Jsoup
                    .connect(url)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();
            
            Element table = doc.select("table").get(1); 
            Elements rows = table.select("tr");

            JSONArray festivalList = new JSONArray();
            
            for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
            	Element row = rows.get(i);
            	
            	Element date = row.select("td").get(0);            	
        		
        		Element place = row.select("td").get(1);            	
        		
        		Element name = row.select("td").get(2);            	
        		
        		Element firstHeldYear = row.select("td").get(3);
        		
        		Element relatedCharacters = row.select("td").get(4);

                JSONObject festival =  new JSONObject();
                
                festival.put("date (lunar calendar)", date.text());
                festival.put("place", place.text());
                festival.put("name", name.text());
                festival.put("firstHeldYear", firstHeldYear.text());
                festival.put("relatedCharacters", relatedCharacters.text());

                festivalList.put(festival);    
            }
            
            FileWriter file = new FileWriter("festival.json");
            file.write(festivalList.toString());
            
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } 


	}

}
