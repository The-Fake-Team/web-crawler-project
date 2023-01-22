package crawler;

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
            
            Element table = doc.select("table").get(0); // select the first table.
            Elements rows = table.select("tr");
            Element name, recognitionDate, place;
            Elements link, detail, description;
                        
            JSONArray historicalSiteList = new JSONArray();
            
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            	
            	JSONObject historicalSite = new JSONObject();
                Element row = rows.get(i); // get row i
                Elements cells = row.select("td"); // get cells in row i
                
                name = cells.select("p").get(1);  
                historicalSite.put("name", name.text());
                
                recognitionDate = cells.select("p").get(2);
                historicalSite.put("recognitionDate", recognitionDate.text().replace("Ngày ", ""));
                
                place = cells.select("p").get(3);
                historicalSite.put("place", place.text());
                
//                try {
//                	
//                	place = cells.select("p").get(5);
//                	placeText = place.text();
//                	
//                	
//                } catch(Exception e) {
//                	recognitionDate = cells.select("span[style=font-size:10.5pt]").get(3);                	
//                	historicalSite.put("date recognized", recognitionDate.text().replace("Ngày ", ""));   	
//                	
//                	if (cells.select("span[style=font-size:10.5pt]").size() >= 5) {
//                		System.out.println(cells.select("span[style=font-size:10.5pt]").size());
//	            		place = cells.select("span[style=font-size:10.5pt]").get(4);
//	            		placeText = place.text();
//                	}
//
//                }
                                
                link = cells.select("a[href]");
                
                if (link.attr("abs:href") != "") {
                	
                	try {
		                doc_link = Jsoup.connect(link.attr("abs:href")).timeout(10000).get();
		                
		                description = doc_link.select("div[class=description]");
		                historicalSite.put("description", description.text());
		                
//		                Unlink to add detail
//		                doc_link.select("strong").remove();
//		                detail = doc_link.select("span[style=font-size:13px]");
//		                                
//		                siteItem.put("detail", detail.text());
                	} catch(Exception e) {
                		System.out.println("Link is unavailable");
                	}
                }
                
                historicalSiteList.put(historicalSite);   
            }
            
            FileWriter file = new FileWriter("historical_site_no_detail.json");
            file.write(historicalSiteList.toString());
            
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } 

	}
}
