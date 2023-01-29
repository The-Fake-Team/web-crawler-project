package crawler.king;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import crawler.utils.Utils;
public class King {

	public static void main(String[] args) throws JSONException, IOException {
		
        String url = "https://vi.wikipedia.org/wiki/Vua_Việt_Nam";
        Document doc = null;
        
        doc = Jsoup
                .connect(url)
                .userAgent("Jsoup client")
                .timeout(20000).get();
        
        Elements docContent = doc.select("#mw-content-text>.mw-parser-output");
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
                    
                    Utils.putToObject(king, tableKeyList, kingValueList);
                    
                    kingList.put(king);
                }

                jsonObjectKingTable.put("kingList", kingList);
                    
                String[] periodArray = Utils.extractInt(titles.get(indexTable).text()).split("\\s+");
                if (periodArray.length > 1 && indexTable+1 < titles.size()) {
                    String[] nextArray = Utils.extractInt(titles.get(indexTable+1).text()).split("\\s+");
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

        FileWriter fwk = new FileWriter("king.json");
        fwk.write(jsonArrayKingTables.toString());
        
        fwk.close();
	}
		
}
