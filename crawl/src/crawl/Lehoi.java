package crawl;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Lehoi {

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
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            	Element row = rows.get(i);
            	
            	Element date = row.select("td").get(0);            	
//        		System.out.println("Date: " + date.text());
        		
        		Element place = row.select("td").get(1);            	
//        		System.out.println("Place: " + place.text());
        		
        		Element name = row.select("td").get(2);            	
//        		System.out.println("Name: " + name.text());
        		
        		JSONObject festival = new JSONObject();
                JSONObject festivalItem =  new JSONObject();
                festivalItem.put("date (lunar calendar)", date.text());
                festivalItem.put("place", place.text());
                festivalItem.put("name", name.text());
                festival.put("festivals list", festivalItem);
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
