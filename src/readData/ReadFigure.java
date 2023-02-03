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

import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.king.King;

public class ReadFigure extends FileInfo implements ReadData<HistoricalFigure> {

    public ReadFigure(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricalFigure> readData() {
        List<HistoricalFigure> figures = new ArrayList<HistoricalFigure>();
        JSONParser jsonpanser = new JSONParser();

        try (FileReader reader = new FileReader(this.getFileName())) {
            Object obj = jsonpanser.parse(reader);
            JSONArray figure = (JSONArray) obj;
            for (int i = 0; i < figure.size(); i++) {
                JSONObject empObject = (JSONObject) figure.get(i);

                // Lấy năm sinh năm mất
                Integer birthYear = null, deathYear = null;
                try {
                    int birth = (int) (long) empObject.get("birthYear");
                    birthYear = birth;
                } catch (Exception e) {
                    birthYear = null;
                }
                try {
                    int death = (int) (long) empObject.get("deathYear");
                    deathYear = death;
                } catch (Exception e) {
                    deathYear = null;
                }

                int id = (int) (long) empObject.get("id");

                //
                String name = (String) empObject.get("name");
                String otherName = (String) empObject.get("other name");
                String description = (String) empObject.get("description");
                String place = (String) empObject.get("place");

                // Lấy thời kỳ
                List<HistoricalPeriod> periodList = new ArrayList<HistoricalPeriod>();
                JSONArray arr = (JSONArray) empObject.get("periods");
                for (int j = 0; j < arr.size(); j++) {
                    JSONObject emp = (JSONObject) arr.get(0);
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
                    String namePeriod = (String) emp.get("name");
                    HistoricalPeriod period = new HistoricalPeriod(namePeriod, start, end);
                    periodList.add(period);
                }

                HistoricalFigure figure1 = new King(id, name, otherName, birthYear, deathYear, description,
                        place, periodList);
                figures.add(figure1);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return figures;

    }

}
