package readData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.*;

import models.festival.Festival;
import models.historicEvent.HistoricEvent;
import models.historicalFigure.HistoricalFigure;

public class ReadEvent<T> extends FileInfo implements ReadData<HistoricEvent> {

    public ReadEvent(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricEvent> readData() throws IOException, FileNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<HistoricEvent> readData(List<HistoricalFigure> figures) {
        List<HistoricEvent> events = new ArrayList<HistoricEvent>();
        JSONParser jsonpanser = new JSONParser();

        try (FileReader reader = new FileReader(this.getFileName())) {
            Object obj = jsonpanser.parse(reader);
            JSONArray Events = (JSONArray) obj;
            for (int i = 0; i < Events.size(); i++) {
                JSONObject empObject = (JSONObject) Events.get(i);

                // Lấy tên + thời gian diễn ra

                JSONObject emp = (JSONObject) empObject.get("duration");
                Integer start = null, end = null;
                try {
                    start = (Integer) emp.get("start");
                } catch (Exception e) {
                    start = null;
                }
                try {
                    end = (Integer) emp.get("end");
                } catch (Exception e) {
                    end = null;
                }

                String name = (String) empObject.get("name"); // Get Name

                //
                String description = (String) empObject.get("detail");
                String references = (String) empObject.get("references");

                // Lấy các site liên quan
                String reLatedPlaces = (String) empObject.get("relatedPlaces");
                HistoricEvent event = new HistoricEvent(name, start, end, references,
                        description, reLatedPlaces);

                // Lấy các nhân vật liên quan
                String ReLatedFigures = (String) empObject.get("relatedFigures");
                String[] str = ReLatedFigures.split(", ");

                JSONArray arr = (JSONArray) empObject.get("relatedFiguresId");
                List<Integer> listID = new ArrayList<Integer>();
                for (int j = 0; j < arr.size(); j++) {

                    Number ID = (Number) arr.get(j);
                    int id = 0;
                    try {
                        id = ID.intValue();
                    } catch (Exception e) {
                        id = 0;
                    }
                    // System.out.println(id);
                    if (id == 0)
                        event.addRelatedFigure(str[j]);
                    else {
                        event.addRelatedFigure(figures.get(id - 1));
                    }
                }

                events.add(event);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return events;
    }

}
