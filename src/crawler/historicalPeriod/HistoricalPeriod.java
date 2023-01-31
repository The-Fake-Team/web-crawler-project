package crawler.historicalPeriod;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import crawler.utils.Utils;

public class HistoricalPeriod implements Runnable{
	
	@Override
	public void run () {
				
        String url = "https://vansu.vn/viet-nam/nien-bieu-lich-su";
        Document doc = null;
        
        try {
  
        	doc = Jsoup
                    .connect(url)
                    .userAgent("Jsoup client")
                    .timeout(20000).get();

            Elements container = doc.select(".container"); 
            Elements historicalPeriods = container.get(1).select("div>b>a:nth-of-type(1)");

            JSONArray historicalPeriodList = new JSONArray();
            
            for (int i = 0; i < historicalPeriods.size(); i++) {

                JSONObject historicalPeriod = new JSONObject();
                
                String historicalPeriodTitle = historicalPeriods.get(i).text();
                
                String historicalPeriodName = historicalPeriodTitle.replaceAll("\\(.+\\)", "").trim(); // only get name of historical event
                historicalPeriod.put("name", historicalPeriodName);
                
                String[] startEnd = Utils.extractInt(historicalPeriodTitle).split("\\s+"); // get start and end time
                
                JSONObject duration = new JSONObject();
                
                if (startEnd.length > 1 && i < historicalPeriods.size()) {
                	
                    String[] nextPeriodStartEnd = Utils.extractInt(historicalPeriods.get(i+1).text()).split("\\s+");
                    
                    int start = Integer.parseInt(startEnd[0]);
                    int end = Integer.parseInt(startEnd[1]);
                    int nextPeriodStart = Integer.parseInt(nextPeriodStartEnd[0]);
                    
                    
                    duration.put("start", start > end ? 0-start : start);
                    duration.put("end", start > end && end >  nextPeriodStart  ? 0-end : end);
                    
                    historicalPeriod.put("duration", duration);
                } else{
                	
                	duration.put("start",startEnd[0] == ""?JSONObject.NULL: Integer.parseInt(startEnd[0]));
                	duration.put("end", JSONObject.NULL);
                	
                	historicalPeriod.put("duration", duration);
                }
                
                historicalPeriodList.put(historicalPeriod);
            }

            
            File file = new File("src\\data\\period.json");
            file.getParentFile().mkdirs();
            FileWriter myWriter = new FileWriter(file);

            myWriter.write(historicalPeriodList.toString());

            myWriter.close();
            
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
	}
}
