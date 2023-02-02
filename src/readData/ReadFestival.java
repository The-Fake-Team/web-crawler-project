package readData;

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

public class ReadFestival extends FileInfo implements ReadData<Festival> {
    public ReadFestival(String fileName) {
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
            JSONArray festival = (JSONArray) obj;

            for (int i = 0; i < festival.size(); i++) {
                JSONObject empObject = (JSONObject) festival.get(i);
                String relatedCharacters = (String) empObject.get("relatedCharacters");
                String name = (String) empObject.get("name");
                String lunarDate = (String) empObject.get("lunarDate");
                int id = (int) (long) empObject.get("id");
                String place = (String) empObject.get("place");
                String firstHeld = (String) empObject.get("firstHeld");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
                Date date = null;
                try {
                    date = sdf.parse(lunarDate);
                } catch (Exception e) {

                }
                String ReLatedFigures = (String) empObject.get("relatedCharacters");
                String[] str = ReLatedFigures.split(", ");
                Festival festival1 = new Festival(id, name, date, place, firstHeld);
                JSONArray arr = (JSONArray) empObject.get("relatedFiguresId");
                List<Integer> listID = new ArrayList<Integer>();
                for (int j = 0; j < arr.size(); j++) {

                    Number ID = (Number) arr.get(j);
                    int id1 = 0;
                    try {
                        id1 = ID.intValue();
                    } catch (Exception e) {
                        id1 = 0;
                    }
                    // System.out.println(id);
                    if (id1 == 0)
                        festival1.addRelatedFigure(str[j]);
                    else {
                        festival1.addRelatedFigure(figures.get(id - 1));
                    }
                }
                festivals.add(festival1);
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
