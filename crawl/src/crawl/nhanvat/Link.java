package crawl.nhanvat;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Link {

	public static void main(String[] args) {
		String url = "https://vansu.vn/viet-nam/viet-nam-nhan-vat";
		Document doc = null;
		String allLink = "";
		
		try {
			
			for (int i = 0; i <= 119; i++) {
				doc = Jsoup
	                    .connect(url + "?page=" + i)
	                    .userAgent("Jsoup client")
	                    .timeout(20000).get();
				
				Element table = doc.select("table").get(0); //select the first table.
				Elements rows = table.select("tr");
				
				for (int j = 1; j < rows.size() - 1; j++) {
					Element row = rows.get(j); // select each row in the table
	                Elements cols = row.select("td"); 
	                String link = cols.select("a[href]").attr("abs:href");
//	                System.out.println(link);
	                
	                allLink += link + "\n";
	                	                		
				}
			}
			FileWriter myWriter = new FileWriter("link_nhan_vat.txt");
			myWriter.write(allLink);
			myWriter.close();
		} catch (IOException e) {
            e.printStackTrace();
		}

	}

}
