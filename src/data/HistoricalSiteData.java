package data;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.*;

import models.historicalSite.HistoricalSite;

public class HistoricalSiteData extends FileInfo implements ReadData<HistoricalSite> {

    public HistoricalSiteData(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricalSite> readData() {

        List<HistoricalSite> historicalSites = new ArrayList<HistoricalSite>();
        
        JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(this.getFileName())) {
        	
            Object obj = jsonParser.parse(reader);
            
            JSONArray historicalSiteObjectList = (JSONArray) obj;
            
            for (int i = 0; i < historicalSiteObjectList.size(); i++) {
            	
                JSONObject historicalSiteObject = (JSONObject) historicalSiteObjectList.get(i);
                
                String name = (String) historicalSiteObject.get("name"); // Get Name
                
                String time = (String) historicalSiteObject.get("recognitionDate"); // Get Time
                
                String place = (String) historicalSiteObject.get("place");
                
                String summary = (String) historicalSiteObject.get("summary");
                
                String description = (String) historicalSiteObject.get("detail");

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                
                Date recognitionDate = new Date();
                
                try {
                	recognitionDate = formatter.parse(time);
                	
                } catch (Exception e) {
                	
                }
                HistoricalSite historicalSite = new HistoricalSite(name, recognitionDate, place, summary, description);
                
                historicalSites.add(historicalSite);
            }
        } catch (IOException | ParseException e) {
        	
            e.printStackTrace();
        } 

        return historicalSites;
    }

}