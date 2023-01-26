package crawler.historyEvent;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkEvent {
    public static void main(String[] args) throws Exception {
    	
        String historicEvenstUrl = "https://thuvienlichsu.com/su-kien";
        String allIndividualEventUrls = "";
        Document doc = null;

        try {
            for (int i = 1; i <= 19; i++) {
                doc = Jsoup
                        .connect(historicEvenstUrl + "?page=" + i)
                        .userAgent("Jsoup client")
                        .timeout(20000).get();

                Elements historicEventList = doc.select(".divide-content");
                Elements historicEventLinkList = historicEventList.select(".click");
                
                for (Element link : historicEventLinkList) {
           
                    String eventUrl = link.select("a[href]").attr("abs:href");
                    allIndividualEventUrls += eventUrl + "\n";
                }
            }
	        System.out.print(allIndividualEventUrls);

            FileWriter myWriter = new FileWriter("historicEventUrls.txt");
            myWriter.write(allIndividualEventUrls);
            myWriter.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
