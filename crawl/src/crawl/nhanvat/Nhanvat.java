package crawl.nhanvat;

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

public class Nhanvat {
	
	public static JSONObject infoFromLink(String link) throws IOException, JSONException {
		Document doc = Jsoup
				        .connect(link)
				        .userAgent("Jsoup client")
				        .timeout(20000).get();
		
		String infoParagraph = "";
    	JSONObject nhanvat = new JSONObject();
        JSONObject nhanvatItem =  new JSONObject();
        
		Elements allInfo = doc.select("p");
		for (Element info: allInfo) {
			infoParagraph += info.text() + "\n";
		}
		
		Element name = doc.select("div.active.section").first();
		System.out.println(name.text());
		nhanvatItem.put("tên", name.text());
		
		Element table = doc.select("table").get(0); //select the first table.
		Elements rows = table.select("tr");
		for (int i = 0; i < rows.size() - 1; i++) {
			Element row = rows.get(i);
			Element key = row.select("td").get(0);
			Element value = row.select("td").get(1);
			
			nhanvatItem.put(key.text(), value.text());
		}
		
		nhanvatItem.put("thông tin chi tiết", infoParagraph);
		nhanvat.put("thông tin nhân vật", nhanvatItem);
		
		return nhanvat;		
	}

	public static void main(String[] args) throws JSONException {
		try {
			JSONArray nhanvatList = new JSONArray();
			
			File myObj = new File("link_nhan_vat.txt");
		    Scanner myReader = new Scanner(myObj);
		    while (myReader.hasNextLine()) {
		        String link = myReader.nextLine();
		        nhanvatList.put(infoFromLink(link));
		     }
	        myReader.close();
	        
	        FileWriter file = new FileWriter("nhan_vat.json");
            file.write(nhanvatList.toString());
            
            file.close();
	        
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	}

}
