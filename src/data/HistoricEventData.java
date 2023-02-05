package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.*;

import models.historicEvent.HistoricEvent;
import models.historicalFigure.HistoricalFigure;

public class HistoricEventData<T> extends FileInfo implements ReadData<HistoricEvent> {

    public HistoricEventData(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricEvent> readData() throws IOException, FileNotFoundException {
        return null;
    }

    public List<HistoricEvent> readData(List<HistoricalFigure> figures) {
    	
        List<HistoricEvent> events = new ArrayList<HistoricEvent>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(this.getFileName())) {
        	
            Object obj = jsonParser.parse(reader);
            
            JSONArray Events = (JSONArray) obj;
            
            for (int i = 0; i < Events.size(); i++) {
            	
                JSONObject eventObject = (JSONObject) Events.get(i);

                JSONObject duration = (JSONObject) eventObject.get("duration");
                Integer start = null, end = null;
                try {
                    start = (Integer) duration.get("start");
                } catch (Exception e) {
                    start = null;
                }
                try {
                    end = (Integer) duration.get("end");
                } catch (Exception e) {
                    end = null;
                }

                String name = (String) eventObject.get("name"); // Get Name

                //
                String description = (String) eventObject.get("detail");
                String references = (String) eventObject.get("references");

                // get related sites
                String reLatedPlaces = (String) eventObject.get("relatedPlaces");
                HistoricEvent event = new HistoricEvent(name, start, end, references,
                        description, reLatedPlaces);

                // get related figures
                String ReLatedFigures = (String) eventObject.get("relatedFigures");
                String[] str = ReLatedFigures.split(", ");

                JSONArray arr = (JSONArray) eventObject.get("relatedFiguresId");
                List<Integer> listID = new ArrayList<Integer>();
                for (int j = 0; j < arr.size(); j++) {

                    Number ID = (Number) arr.get(j);
                    int id = 0;
                    try {
                        id = ID.intValue();
                    } catch (Exception e) {
                        id = 0;
                    }
                    
                    if (id == 0)
                        event.addRelatedFigure(str[j]);
                    else {
                        event.addRelatedFigure(figures.get(id - 1));
                    }
                }

                events.add(event);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } 

        return events;
    }

}