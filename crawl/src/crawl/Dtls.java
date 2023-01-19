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

public class Dtls {

	public static void main(String[] args) throws JSONException {
		String url = "http://dsvh.gov.vn/danh-muc-di-tich-quoc-gia-dac-biet-1752";
        Document doc = null;
        Document doc_link;

        try {

            doc = Jsoup
                    .connect(url)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();
            
            Element table = doc.select("table").get(0); //select the first table.
            Elements rows = table.select("tr");
            Element name, date_recognize, place;
            Elements link, detail, description;
            
            String placeText;
            
            JSONArray siteList = new JSONArray();
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            	JSONObject site = new JSONObject();
                JSONObject siteItem =  new JSONObject();
            	System.out.println(i);
                Element row = rows.get(i);
                Elements cols = row.select("td");        
                
                name = cols.select("span[style=font-size:10.5pt]").get(1);  
                siteItem.put("name", name.text());
                placeText = "";
                try {
                	place = cols.select("span[style=font-size:10.5pt]").get(5);
                	placeText = place.text();
                	
                	date_recognize = cols.select("span[style=font-size:10.5pt]").get(4);
                	siteItem.put("date recognized", date_recognize.text());
                } catch(Exception e) {
                	date_recognize = cols.select("span[style=font-size:10.5pt]").get(3);                	
                	siteItem.put("date recognized", date_recognize.text().replace("Ngày ", ""));   	
                	
                	if (cols.select("span[style=font-size:10.5pt]").size() >= 5) {
                		System.out.println(cols.select("span[style=font-size:10.5pt]").size());
	            		place = cols.select("span[style=font-size:10.5pt]").get(4);
	            		placeText = place.text();
                	}

                }
                
                siteItem.put("place", placeText);               
                
                link = cols.select("a[href]");
                if (link.attr("abs:href") != "") {    
                	try {
		                doc_link = Jsoup.connect(link.attr("abs:href")).timeout(10000).get();
		                
		                description = doc_link.select("div[class=description]");
		                siteItem.put("description", description.text());
		                
//		                Unlink to add detail
//		                doc_link.select("strong").remove();
//		                detail = doc_link.select("span[style=font-size:13px]");
//		                                
//		                siteItem.put("detail", detail.text());
                	} catch(Exception e) {
                		System.out.println("Link is unavailable");
                	}
                }
                
                site.put("festivals list", siteItem);
                siteList.put(site);             
            }
            
            FileWriter file = new FileWriter("historical_site_no_detail.json");
            file.write(siteList.toString());
            
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } 

	}

}
