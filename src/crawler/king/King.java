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
        
        JSONArray ErasWithKingList = new JSONArray();

        int indexTable = 0;
        
        for(int i = 0; i < kingsTables.size(); i++) {
        	
            Elements tableKeyList = kingsTables.get(i).select("tr:nth-of-type(1)>th"); // get individual table's key list
            Elements tableValueList = kingsTables.get(i).select("tr:not(:nth-of-type(1))"); // get individual table's value list

            if (tableKeyList.size() > 0) { // remove empty table
            	
                JSONObject eraWithKingList = new JSONObject();
                JSONArray kingList = new JSONArray();

                for (Element value : tableValueList) {
                	
                    JSONObject king = new JSONObject();
                    
                    Elements kingValueList = value.select("td");
                    
                    putToObject(king, tableKeyList, kingValueList);
                    
                    kingList.put(king);
                }

                eraWithKingList.put("kingList", kingList);
                    
                String[] periodArray = Utils.extractInt(titles.get(indexTable).text()).split("\\s+");
                
                JSONObject time = new JSONObject();
                
                if (periodArray.length > 1 && indexTable+1 < titles.size()) {
                	
                    String[] nextArray = Utils.extractInt(titles.get(indexTable+1).text()).split("\\s+");
                    
                    int start = Integer.parseInt(periodArray[0]);
                    int end = Integer.parseInt(periodArray[1]);
                    int nextStart = Integer.parseInt(nextArray[0]);
                    
                    
                    time.put("start", start > end ? 0-start : start);
                    time.put("end", start > end && end > nextStart  ? 0-end : end);
                    eraWithKingList.put("time", time);
                    
                } else if (indexTable == titles.size()-1) {
                	
                	time.put("start", Integer.parseInt(periodArray[0]));
                	time.put("end", Integer.parseInt(periodArray[1]));
                    eraWithKingList.put("time", time);
                }

                eraWithKingList.put("period", titles.get(indexTable).text().replaceAll("\\(([^\\]]+)\\)", "").trim());

                System.out.println("Get table " + (indexTable) + " successfully!!");
                indexTable++;
                ErasWithKingList.put(eraWithKingList);
            } 
        }

        FileWriter fwk = new FileWriter("king.json");
        fwk.write(ErasWithKingList.toString());
        
        fwk.close();
	}
	
	 public static void putToObject (JSONObject object, Elements keys, Elements values) throws JSONException {

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
	            object.put(keys.get(lastKeyIndex).wholeOwnText().replace("\n", "").trim(), lastValue);
	    }
		
}
