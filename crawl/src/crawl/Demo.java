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
                        values.get(j).ownText()
                                .replaceAll("\\[([^\\]]+)\\]", ",")
                                .replaceAll("\\(([^\\]]+)\\)", ",")
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
                        .replaceAll("\\[([^\\]]+)\\]", ",")
                        .replaceAll("\\(([^\\]]+)\\)", ",")
                        .trim();
                object.put(key, value);
            }
            object.put(
                    keys.get(indexLastValue).wholeOwnText().replace("\n", "").trim(), lastValue);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    static String extractInt(String str)
    {
        // Replacing every non-digit number
        // with a space(" ")
        str = str.replaceAll("[^\\d]", " ");
 
        // Remove extra spaces from the beginning
        // and the ending of the string
        str = str.trim();
 
        // Replace all the consecutive white
        // spaces with a single space
        str = str.replaceAll(" + ", " ");
 
        if (str.equals(" "))
            return "-1";
        return str;
    }

    public static void main(String[] args) throws JSONException {
        String url = "https://vansu.vn/viet-nam/nien-bieu-lich-su";
        Document doc = null;

        String url2 = "https://vi.wikipedia.org/wiki/Vua_Việt_Nam";
        Document doc2 = null;
        try {
            /**
             * Crawl data from https://vansu.vn/viet-nam/nien-bieu-lich-su
             */
            doc = Jsoup
                    .connect(url)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();

            Elements container = doc.select(".container");
            Elements listPeriod = container.get(1).select("div>b>a:nth-of-type(1)");

            JSONArray jsonArrayPeriod = new JSONArray();
            for (int i = 0; i < listPeriod.size(); i++) {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("name", listPeriod.get(i).text().replaceAll("\\(([^\\]]+)\\)", "").trim());
                String[] myArray = extractInt(listPeriod.get(i).text()).split("\\s+");
                if (myArray.length > 1 && i+1 <= listPeriod.size()) {
                    String[] nextArray = extractInt(listPeriod.get(i+1).text()).split("\\s+");
                    int start = Integer.parseInt(myArray[0]);
                    int end = Integer.parseInt(myArray[1]);
                    int nextStart = Integer.parseInt(nextArray[0]);
                    jsonObject.put("start", start > end ? 0-start : start);
                    jsonObject.put("end", start > end && end >  nextStart  ? 0-end : end);
                } else{
                    jsonObject.put("start",myArray[0] == ""?JSONObject.NULL: Integer.parseInt(myArray[0]));
                    jsonObject.put("end", JSONObject.NULL);
                }
                jsonArrayPeriod.put(jsonObject);
            }

            // JSONObject mainObj = new JSONObject();
            // mainObj.put("Period", jsonArrayPeriod);

            FileWriter fw = new FileWriter("period.json");
            fw.write(jsonArrayPeriod.toString());

            fw.close();

            ////////////////////////////////
            /**
             * Crawl data from https://vi.wikipedia.org/wiki/Vua_Việt_Nam
             */
            doc2 = Jsoup
                    .connect(url2)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();
            Elements docContent = doc2.select("#mw-content-text>.mw-parser-output");
            Elements tablesKing = docContent.select("table:not(:nth-of-type(1)):not(:nth-last-of-type(1))");
            Elements titles = docContent.select("h3>span.mw-headline");
            JSONArray jsonArrayKingTables = new JSONArray();

            int indexTable = 0;
            for (int i=0; i<tablesKing.size(); i++) {
                /// Tìm các element chứa keys và values cần
                Elements listKeyTables = tablesKing.get(i).select("tr:nth-of-type(1)>th");
                Elements listValueTables = tablesKing.get(i).select("tr:not(:nth-of-type(1))");

                if (listKeyTables.size() > 0) { /// Loại bỏ những bảng không có dữ liệu
                    JSONObject jsonObjectKingTable = new JSONObject();
                    JSONArray jsonArrayKing = new JSONArray();

                    for (Element elementValue : listValueTables) {
                        JSONObject jsonObjecKing = new JSONObject();
                        Elements listValueKing = elementValue.select("td");
                        putToObject(jsonObjecKing, listKeyTables, listValueKing);
                        jsonArrayKing.put(jsonObjecKing);
                    }

                    jsonObjectKingTable.put("list king", jsonArrayKing);
                        
                    String[] periodArray = extractInt(titles.get(indexTable).text()).split("\\s+");
                    if (periodArray.length > 1 && indexTable+1 < titles.size()) {
                        String[] nextArray = extractInt(titles.get(indexTable+1).text()).split("\\s+");
                        int start = Integer.parseInt(periodArray[0]);
                        int end = Integer.parseInt(periodArray[1]);
                        int nextStart = Integer.parseInt(nextArray[0]);
                        jsonObjectKingTable.put("period start", start > end ? 0-start : start);
                        jsonObjectKingTable.put("period end", start > end && end > nextStart  ? 0-end : end);
                    } else if (indexTable == titles.size()-1) {
                        jsonObjectKingTable.put("period start", Integer.parseInt(periodArray[0]));
                        jsonObjectKingTable.put("period end", Integer.parseInt(periodArray[1]));
                    }

                    // jsonObjectKingTable.put("period", titles.get(indexTable).text());
                    jsonObjectKingTable.put("name period", titles.get(indexTable).text().replaceAll("\\(([^\\]]+)\\)", "").trim());

                    System.out.println("Get table " + (indexTable) + " successfully!!");
                    indexTable++;
                    jsonArrayKingTables.put(jsonObjectKingTable);
                } 
            }

            // JSONObject allKingTables = new JSONObject();
            // allKingTables.put("All king Tables", jsonArrayKingTables);

            FileWriter fwk = new FileWriter("testoutKing.json");
            fwk.write(jsonArrayKingTables.toString());
            
            fwk.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
