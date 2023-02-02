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

import models.historicalPeriod.HistoricalPeriod;

public class ReadPeriod extends FileInfo implements ReadData<HistoricalPeriod>  {

    public ReadPeriod(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricalPeriod> readData()  {
        List<HistoricalPeriod> periods = new ArrayList<HistoricalPeriod>();
        JSONParser jsonpanser = new JSONParser();     
        try (FileReader reader = new FileReader(this.getFileName())) {
            Object obj = jsonpanser.parse(reader);
            JSONArray period = (JSONArray) obj;
            for (int i = 0; i < period.size(); i++) {
                JSONObject emp = (JSONObject) period.get(i);

                // Lấy năm bắt đầu năm kết thúc
                JSONObject empObject = (JSONObject) emp.get("duration");

                Integer start = null, end = null;
                try {
                    int a = (int) (long) empObject.get("start");
                    start = a;
                } catch (Exception e) {
                    start = null;
                }
                try {
                    int b = (int) (long) empObject.get("end");
                    end = b;
                } catch (Exception e) {
                    end = null;
                }

                // Lấy tên thời kỳ
                String name = (String) emp.get("name");

                HistoricalPeriod period1 = new HistoricalPeriod(name, start, end);
                periods.add(period1);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return periods;

    }

}
