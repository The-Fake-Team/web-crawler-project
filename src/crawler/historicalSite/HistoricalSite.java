package crawler.historicalSite;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HistoricalSite {

	public static void main(String[] args) throws JSONException {
		
		String url = "http://dsvh.gov.vn/danh-muc-di-tich-quoc-gia-dac-biet-1752"; 
        Document allSitedoc = null;
        Document individualSiteDoc = null;

        try {
        	
        	allSitedoc = Jsoup
                    .connect(url)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();
            
            Element table = allSitedoc.select("table").get(0); // select the first table.
            Elements rows = table.select("tr");
            Element name, recognitionDate, place, description;
            Elements detail;
            String historicalSiteUrl;
                        
            JSONArray historicalSiteList = new JSONArray();
            
            for (int i = 1; i < rows.size(); i++) { // first row is the col names so skip it.
            	
            	JSONObject historicalSite = new JSONObject();
                Element row = rows.get(i); // get row i
                Elements cells = row.select("td"); // get cells in row i
                
                name = cells.get(1).select("p").get(0);  
                historicalSite.put("name", name.text());
                
                if (cells.get(2).select("p").size() == 2) {                	
                	recognitionDate = cells.get(2).select("p").get(1);
                } else {
                	recognitionDate = cells.get(2).select("p").get(0);
                }
                
                historicalSite.put("recognitionDate", recognitionDate.text().replace("Ngày ", "")); // remove "Ngay"
                
                place = cells.get(3).select("p").get(0);
                historicalSite.put("place", place.text());
                                
                historicalSiteUrl = cells.select("a[href]").attr("abs:href");
                
                if (historicalSiteUrl != "") {
                	
                	try {
                		
                		individualSiteDoc = Jsoup.connect(historicalSiteUrl)
		                						 .timeout(5000)
		                						 .get();
                		
                		description = individualSiteDoc.select("div[class=description]").get(0);
                		
		                historicalSite.put("description", description.text());
		                
		                String detailContent = "";
		                
		                detail = description.siblingElements();
		                
		                for (int j = 0; j < detail.size(); j ++) {
		                	
		                	if (j == 0) {
		                		detailContent += detail.get(j).text() + "\n";
		                	} else {		                		
		                		detailContent += detail.get(j).text();
		                	}
		                }
		                                
		                historicalSite.put("detail", detailContent);
                	} catch(Exception e) {
                		System.out.println("Link is unavailable");
                	}
                } else {
                	historicalSite.put("description", "");
                	historicalSite.put("detail", "");
                }
                
                historicalSiteList.put(historicalSite);
            }
            
            FileWriter file = new FileWriter("historical_site.json");
            file.write(historicalSiteList.toString());
            
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        } 

	}
}