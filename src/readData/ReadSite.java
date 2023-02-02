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

import models.historicalSite.HistoricalSite;

public class ReadSite extends FileInfo implements ReadData<HistoricalSite> {

    public ReadSite(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricalSite> readData() {

        List<HistoricalSite> sites = new ArrayList<HistoricalSite>();
        JSONParser jsonpanser = new JSONParser();
        try (FileReader reader = new FileReader(this.getFileName())) {
            Object obj = jsonpanser.parse(reader);
            JSONArray site = (JSONArray) obj;
            for (int i = 0; i < site.size(); i++) {
                JSONObject empObject = (JSONObject) site.get(i);
                String Name = (String) empObject.get("name"); // Get Name
                String time = (String) empObject.get("recognitionDate"); // Get Time
                String place = (String) empObject.get("place");
                String summary = (String) empObject.get("summary");
                String description = (String) empObject.get("detail");

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date RecognitionDate = new Date();
                try {
                    RecognitionDate = formatter.parse(time);
                } catch (Exception e) {
                }
                HistoricalSite site1 = new HistoricalSite(Name, RecognitionDate, place,
                        summary, description);
                sites.add(site1);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        return sites;
    }

}
