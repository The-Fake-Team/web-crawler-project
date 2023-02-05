package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.*;

import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.king.King;

public class HistoricalFigureData extends FileInfo implements ReadData<HistoricalFigure> {

    public HistoricalFigureData(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricalFigure> readData() {
    	
        List<HistoricalFigure> figures = new ArrayList<HistoricalFigure>();
        
        JSONParser jsonpanser = new JSONParser();
        
        try (FileReader reader = new FileReader(this.getFileName())) {
        	
            Object obj = jsonpanser.parse(reader);
            
            JSONArray figureObjectList = (JSONArray) obj;
            
            for (int i = 0; i < figureObjectList.size(); i++) {
            	
                JSONObject figureObject = (JSONObject) figureObjectList.get(i);

                Integer birthYear = null, deathYear = null;
                
                try {
                	if (figureObject.get("birthYear") != null) { 
                		
                		int birth = (int) (long) figureObject.get("birthYear");
                		
                		birthYear = birth;
                	} else {
                		
                		birthYear = 0;
                	}
                } catch (Exception e) {
                	
                    birthYear = null;
                }
                try {
                	if (figureObject.get("deathYear") != null) {
                		
                		int death = (int) (long) figureObject.get("deathYear");
                		
                		deathYear = death;
                	} else {
                		
                		deathYear =0;
                	}
                    
                } catch (Exception e) {
                	
                    deathYear = null;
                }

                int id = (int) (long) figureObject.get("id");

                String name = (String) figureObject.get("name");
                
                String otherName = (String) figureObject.get("other name");
                
                String description = (String) figureObject.get("description");
                
                String place = (String) figureObject.get("place");

                List<HistoricalPeriod> periodList = new ArrayList<HistoricalPeriod>();
                
                JSONArray periods = (JSONArray) figureObject.get("periods");
                
                for (int j = 0; j < periods.size(); j++) {
                	
                    JSONObject emp = (JSONObject) periods.get(j);
                    
                    Integer start = null, end = null;
                    
                    try {
                    	
                        int a = (int) (long) emp.get("start");
                        start = a;
                        
                    } catch (Exception e) {
                    	
                        start = null;
                    }
                    
                    try {
                        int b = (int) (long) emp.get("end");
                        end = b;
                    } catch (Exception e) {
                        end = null;
                    }
                    
                    String periodName = (String) emp.get("name");
                    
                    HistoricalPeriod period = new HistoricalPeriod(periodName, start, end);
                    
                    periodList.add(period);
                }

                
                HistoricalFigure figure1 = new King(id, name, otherName, birthYear, deathYear, place,
                        description, periodList);                
                figures.add(figure1);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        
        return figures;

    }

}