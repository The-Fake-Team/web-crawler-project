import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LinkEvent {
    public static void main(String[] args) throws Exception {
        String url = "https://thuvienlichsu.com/su-kien";
        Document doc = null;
        String allLink = "";

        try {
            for (int i = 1; i <= 19; i++) {
                doc = Jsoup
                        .connect(url + "?page=" + i)
                        .userAgent("Jsoup client")
                        .timeout(20000).get();

                Elements listContent = doc.select(".divide-content");
                Elements listLink = listContent.select(".click");
                System.out.println(listLink);
                for (Element linkElement : listLink) {
                    String link = linkElement.select("a[href]").attr("abs:href");
                    System.out.println(link);

                    allLink += link + "\n";

                }
            }
            FileWriter myWriter = new FileWriter("history_event_page.txt");
            myWriter.write(allLink);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
