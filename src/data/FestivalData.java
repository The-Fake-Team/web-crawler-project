package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.*;

import models.festival.Festival;
import models.historicalFigure.HistoricalFigure;

public class FestivalData extends FileInfo implements ReadData<Festival> {
	
    public FestivalData(String fileName) {
        super(fileName);
    }

    @Override
    public List<Festival> readData() throws IOException, FileNotFoundException {
        return null;
    }

    public List<Festival> readData(List<HistoricalFigure> figures) {
    	
        List<Festival> festivals = new ArrayList<Festival>();
        
        JSONParser jsonpanser = new JSONParser();
        
        try (FileReader reader = new FileReader(this.getFileName())) {
        	
            Object obj = jsonpanser.parse(reader);
            
            JSONArray festivalObjectList = (JSONArray) obj;

            for (int i = 0; i < festivalObjectList.size(); i++) {
            	
                JSONObject festivalObject = (JSONObject) festivalObjectList.get(i);
                
                String relatedCharacters = (String) festivalObject.get("relatedCharacters");
                
                int id = (int) (long) festivalObject.get("id");
                
                String name = (String) festivalObject.get("name");
                
                String lunarDate = (String) festivalObject.get("lunarDate");
                
                String place = (String) festivalObject.get("place");
                
                String firstHeld = (String) festivalObject.get("firstHeld");
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
                
                Date date = null;
                
                try {
                    date = sdf.parse(lunarDate);
                } catch (Exception e) {
                	
                }
                
                String reLatedFigures = (String) festivalObject.get("relatedCharacters");
                
                String[] str = reLatedFigures.split(", ");
                
                Festival festival = new Festival(id, name, date, place, firstHeld);
                
                JSONArray arr = (JSONArray) festivalObject.get("relatedFiguresId");
                
                List<Integer> listID = new ArrayList<Integer>();
                
                for (int j = 0; j < arr.size(); j++) {

                    Number ID = (Number) arr.get(j);
                    int id1 = 0;
                    try {
                        id1 = ID.intValue();
                    } catch (Exception e) {
                        id1 = 0;
                    }
                    
                    if (id1 == 0)
                    	festival.addRelatedFigure(str[j]);
                    else {
                    	festival.addRelatedFigure(figures.get(id - 1));
                    }
                }
                festivals.add(festival);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return festivals;
    }
}