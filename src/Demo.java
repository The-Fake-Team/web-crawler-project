
/**
 * Author: NTNuan
 * Demo Crawl data từ trang các web dùng Jsoup và JSON
 * 9/1/2023
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class Demo {

    /// Hàm put dữ liệu vào object theo keys và values đã thu thập được từ các
    /// element
    public static void putToObject(JSONObject object, Elements keys, Elements values) {

        /// Vì số values có thể nhiểu hơn số keys
        /// Số values nhiều hơn keys do keys cuối "Trị vì" có 3 values là "năm lên
        /// ngôi", "-", "năm kết thúc"
        /// Tạo lastValue
        String lastValue = "";
        int indexLastValue = keys.size() - 1;
        if (values.size() >= keys.size()) {
            for (int j = indexLastValue; j < values.size(); j++) {
                lastValue = lastValue.concat(
                        values.get(j).wholeText()
                                // Làm sạch các kí tự thừa
                                .replace("\n", "")
                                .replaceAll("[\u2013\u2014.]", "-")
                                .trim());
            }
        }
        // System.out.println("lastValue=" + lastValue);

        /// Put dữ liệu
        try {
            for (int i = 0; i < indexLastValue; i++) {
                String key = keys.get(i).wholeOwnText()
                        .replace("\n", "")
                        .trim();
                String value = values.get(i).wholeText()
                        .replace("\n", "")
                        .replaceAll("[\u2013\u2014.]", "-")
                        .trim();
                object.put(key, value);
            }
            object.put(
                    keys.get(indexLastValue).wholeOwnText().replace("\n", "").trim(), lastValue);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "https://nguoikesu.com";
        Document doc = null;

        String url2 = "https://vi.wikipedia.org/wiki/Vua_Việt_Nam";
        Document doc2 = null;
        try {

            /**
             * Crawl data from https://nguoikesu.com
             */
            doc = Jsoup
                    .connect(url)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();

            Elements lstArticles = doc.select("#jm-left");
            Elements listElement0 = lstArticles.select("ul.jm-red.list-categories.title-star-ms>li.level-0");

            JSONArray jsonArrayElement0 = new JSONArray();
            for (Element element0 : listElement0) {
                Elements listElement1 = element0.select("ul>li.level-1>a");

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArrayElement1 = new JSONArray();

                for (Element element1 : listElement1) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("name", element1.text());
                    jsonArrayElement1.put(jsonObject1);
                }

                jsonObject.put("name", element0.select(">a").text());
                jsonObject.put("list", jsonArrayElement1);
                jsonArrayElement0.put(jsonObject);
            }

            JSONObject mainObj = new JSONObject();
            mainObj.put("Trieu dai", jsonArrayElement0);

            FileWriter fw = new FileWriter("testout.json");
            fw.write(mainObj.toString());

            fw.close();

            ////////////////////////////////
            /**
             * Crawl data from https://vi.wikipedia.org/wiki/Vua_Việt_Nam
             */
            doc2 = Jsoup
                    .connect(url2)
                    .userAgent("Jsoup client")
                    .timeout(50000).get();
            Elements tablesKing = doc2.select(".mw-parser-output>table:not(:nth-of-type(1)):not(:nth-last-of-type(1))");

            JSONArray jsonArrayKingTables = new JSONArray();

            int indexTable = 1;
            for (Element element : tablesKing) {
                /// Tìm các element chứa keys và values cần
                Elements listKeyTables = element.select("tr:nth-of-type(1)>th");
                Elements listValueTables = element.select("tr:not(:nth-of-type(1))");

                if (listKeyTables.size() > 0) { /// Loại bỏ những bảng không có dữ liệu
                    JSONObject jsonObjectKingTable = new JSONObject();
                    JSONArray jsonArrayKing = new JSONArray();

                    for (Element elementValue : listValueTables) {
                        JSONObject jsonObjecKing = new JSONObject();
                        Elements listValueKing = elementValue.select("td");
                        putToObject(jsonObjecKing, listKeyTables, listValueKing);
                        jsonArrayKing.put(jsonObjecKing);
                    }

                    jsonObjectKingTable.put("name", "Table " + indexTable);
                    jsonObjectKingTable.put("list king", jsonArrayKing);

                    System.out.println("Get table " + indexTable + " successfully!!");
                    indexTable++;

                    jsonArrayKingTables.put(jsonObjectKingTable);
                }
            }

            JSONObject allKingTables = new JSONObject();
            allKingTables.put("All king Tables", jsonArrayKingTables);

            FileWriter fwk = new FileWriter("testoutKing.json");
            fwk.write(allKingTables.toString());

            fwk.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException j) {
            j.printStackTrace();
        }
    }
}