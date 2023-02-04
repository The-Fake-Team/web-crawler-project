package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.*;

import models.historicalPeriod.HistoricalPeriod;

public class HistoricalPeriodData extends FileInfo implements ReadData<HistoricalPeriod>  {

    public HistoricalPeriodData(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricalPeriod> readData()  {
    	
        List<HistoricalPeriod> periods = new ArrayList<HistoricalPeriod>();
        
        JSONParser jsonParser = new JSONParser();
        
        try (FileReader reader = new FileReader(this.getFileName())) {
        	
            Object obj = jsonParser.parse(reader);
            JSONArray periodObjectList = (JSONArray) obj;
            
            for (int i = 0; i < periodObjectList.size(); i++) {
            	
                JSONObject periodObject = (JSONObject) periodObjectList.get(i);

                JSONObject duration = (JSONObject) periodObject.get("duration");

                Integer start = null, end = null;
                                
                try {
                    int a = (int) (long) duration.get("start");
                    start = a;
                } catch (Exception e) {
                    start = null;
                }
                try {
                    int b = (int) (long) duration.get("end");
                    end = b;
                } catch (Exception e) {
                    end = null;
                }

                String name = (String) periodObject.get("name");

                HistoricalPeriod period = new HistoricalPeriod(name, start, end);
                
                periods.add(period);
            }

            
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } 

        return periods;
    }

}