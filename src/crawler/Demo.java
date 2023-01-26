package crawler;

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

    public static void putToObject(JSONObject object, Elements keys, Elements values) {

        /// Because the number of value can be more than the number of key
        /// The number of values is more than the keys because the last key "reign" has 3 values as "start reigning year
        /// ", "-", "end reigning year"
    	
        String lastValue = "";
        int lastKeyIndex = keys.size() - 1;
        
        if (values.size() >= keys.size()) {
            for (int j = lastKeyIndex; j < values.size(); j++) {
                lastValue = lastValue.concat(
                        values.get(j).ownText()
                                .replaceAll("\\[([^\\]]+)\\]", ",")
                                .replaceAll("\\(([^\\]]+)\\)", ",")
                                .trim());
            }
        }

        try {
            for (int i = 0; i < lastKeyIndex; i++) {
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
                    keys.get(lastKeyIndex).wholeOwnText().replace("\n", "").trim(), lastValue);
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
        String url1 = "https://vansu.vn/viet-nam/nien-bieu-lich-su";
        Document doc1 = null;

        String url2 = "https://vi.wikipedia.org/wiki/Vua_Việt_Nam";
        Document doc2 = null;
        try {
            /**
             * Crawl data from https://vansu.vn/viet-nam/nien-bieu-lich-su
             */
        	doc1 = Jsoup
                    .connect(url1)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();

            Elements container = doc1.select(".container"); 
            Elements historicalPeriods = container.get(1).select("div>b>a:nth-of-type(1)");

            JSONArray historicalPeriodList = new JSONArray();
            
            for (int i = 0; i < historicalPeriods.size(); i++) {

                JSONObject historicalPeriod = new JSONObject();
                
                String historicalPeriodTitle = historicalPeriods.get(i).text();
                
                String historicalPeriodName = historicalPeriodTitle.replaceAll("\\(.+\\)", "").trim(); // only get name of historical event
                historicalPeriod.put("name", historicalPeriodName);
                
                String[] startEnd = extractInt(historicalPeriodTitle).split("\\s+"); // get start and end time
                
                if (startEnd.length > 1 && i < historicalPeriods.size()) {
                	
                    String[] nextPeriodStartEnd = extractInt(historicalPeriods.get(i+1).text()).split("\\s+");
                    
                    int start = Integer.parseInt(startEnd[0]);
                    int end = Integer.parseInt(startEnd[1]);
                    
                    int nextPeriodStart = Integer.parseInt(nextPeriodStartEnd[0]);
                    
                    historicalPeriod.put("start", start > end ? 0-start : start);
                    historicalPeriod.put("end", start > end && end >  nextPeriodStart  ? 0-end : end);
                    
                } else{
                	historicalPeriod.put("start",startEnd[0] == ""?JSONObject.NULL: Integer.parseInt(startEnd[0]));
                	historicalPeriod.put("end", JSONObject.NULL);
                }
                
                historicalPeriodList.put(historicalPeriod);
            }

            FileWriter myWriter = new FileWriter("period.json");
            myWriter.write(historicalPeriodList.toString());

            myWriter.close();
            
            /**
             * Crawl data from https://vi.wikipedia.org/wiki/Vua_Việt_Nam
             */
            
            doc2 = Jsoup
                    .connect(url2)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();
            
            Elements docContent = doc2.select("#mw-content-text>.mw-parser-output");
            Elements kingsTables = docContent.select("table:not(:nth-of-type(1)):not(:nth-last-of-type(1))");
            Elements titles = docContent.select("h3>span.mw-headline");
            
            JSONArray jsonArrayKingTables = new JSONArray();

            int indexTable = 0;
            
            for(int i = 0; i < kingsTables.size(); i++) {
            	
                Elements tableKeyList = kingsTables.get(i).select("tr:nth-of-type(1)>th"); // get individual table's key list
                Elements tableValueList = kingsTables.get(i).select("tr:not(:nth-of-type(1))"); // get individual table's value list

                if (tableKeyList.size() > 0) { // remove empty table
                	
                    JSONObject jsonObjectKingTable = new JSONObject();
                    JSONArray kingList = new JSONArray();

                    for (Element value : tableValueList) {
                    	
                        JSONObject king = new JSONObject();
                        
                        Elements kingValueList = value.select("td");
                        
                        putToObject(king, tableKeyList, kingValueList);
                        
                        kingList.put(king);
                    }

                    jsonObjectKingTable.put("kingList", kingList);
                        
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
