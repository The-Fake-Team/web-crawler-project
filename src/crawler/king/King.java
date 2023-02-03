package crawler.king;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.JSONArray;
import org.json.JSONException;

import crawler.utils.Utils;
public class King implements Runnable{

	@Override
	public void run() {
		
        String url = "https://vi.wikipedia.org/wiki/Vua_Việt_Nam";
        Document doc = null;
        
        try {
        	
        doc = Jsoup
                .connect(url)
                .userAgent("Jsoup client")
                .timeout(20000).get();
        
        Elements docContent = doc.select("#mw-content-text>.mw-parser-output");
        
        Elements kingsTables = docContent.select("table:not(:nth-of-type(1)):not(:nth-last-of-type(1))");
        
        Elements titles = docContent.select("h3>span.mw-headline");
        
        JSONArray ErasWithKingList = new JSONArray();

        int indexTable = 0;
        
        String[] optionalFields = {"Thụy hiệu", "Vua", "Thế thứ", "Miếu hiệu", "Hoàng đế", 
        		"Thủ lĩnh", "Tiết độ sứ", "Tôn hiệu", "Tôn hiệu hoặc Thụy hiệu", 
        "Tước hiệu"};
        
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
                    
                    // check for empty fields
                    for (String field: optionalFields) {
                    	
	    				if (!king.has(field)) {
	    					
	    					if (field.equals("Thụy hiệu") || field.equals("Miếu hiệu") || field.equals("Tôn hiệu")) {
	    						
	    						king.put(field, "không có");
	    					}
	    					else if (field.equals("Tôn hiệu hoặc Thụy hiệu")) {
	    						
	    						king.put(field, "không có,");
	    					}
	    					else {
	    						
	    						king.put(field, "");
	    					}
	    				}
                    }
                    
                    kingList.put(king);
                }

                eraWithKingList.put("kingList", kingList);
                    
                String[] periodArray = Utils.extractInt(titles.get(indexTable).text()).split("\\s+");
                
                JSONObject time = new JSONObject();
                time.put("start", JSONObject.NULL);
                time.put("end", JSONObject.NULL);
                
                if (periodArray.length > 1 && indexTable+1 < titles.size()) {
                	
                    String[] nextArray = Utils.extractInt(titles.get(indexTable+1).text()).split("\\s+");
                    
                    int start = Integer.parseInt(periodArray[0]);
                    int end = Integer.parseInt(periodArray[1]);
                    int nextStart = Integer.parseInt(nextArray[0]);
                         
                    time.put("start", start > end ? 0-start : start);
                    time.put("end", start > end && end > nextStart  ? 0-end : end);                    
                } else if (indexTable == titles.size()-1) {
                	
                	time.put("start", Integer.parseInt(periodArray[0]));
                	time.put("end", Integer.parseInt(periodArray[1]));
                }
                eraWithKingList.put("time", time);
                
                String nationalName = titles.get(indexTable).text().replaceAll("\\(([^\\]]+)\\)", "").trim();
                eraWithKingList.put("nationalName", nationalName);
                if(time.get("start") != JSONObject.NULL && time.get("end") != JSONObject.NULL) {
                    String periodName = Utils.findPeriodName(time.getInt("start"), time.getInt("end"), nationalName);
                    eraWithKingList.put("period", periodName);
                }

                System.out.println("Get table " + (indexTable) + " successfully!!");
                indexTable++;
                ErasWithKingList.put(eraWithKingList);
            } 
        }

        File file = new File("src\\data\\king.json");
        file.getParentFile().mkdirs();
        FileWriter myWriter = new FileWriter(file);        
        
        myWriter.write(ErasWithKingList.toString());
        
        myWriter.close();
        
        } catch (JSONException | IOException | ParseException e) {
        	e.printStackTrace();
        }
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
