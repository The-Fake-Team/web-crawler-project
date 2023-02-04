package readData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.king.King;

public class ReadKing extends FileInfo implements ReadData<HistoricalFigure> {
    public ReadKing(String fileName) {
        super(fileName);
    }

    @Override
    public List<HistoricalFigure> readData() {
        return null;

    }

    public List<HistoricalFigure> readData(List<HistoricalFigure> figures) {
        List<King> kings = new ArrayList<King>();
        JSONParser jsonpanser = new JSONParser();

        try (FileReader reader = new FileReader(this.getFileName())) {
            Object obj = jsonpanser.parse(reader);
            JSONArray Kings = (JSONArray) obj;
            for (int i = 0; i < Kings.size(); i++) {
                JSONObject empObject = (JSONObject) Kings.get(i);
                JSONArray arr = (JSONArray) empObject.get("kingList");
                Number a = (Number) empObject.get("period start");
                Number b = (Number) empObject.get("period end");
                String name = (String) empObject.get("name period");
                int start, end;
                try {
                    start = a.intValue();
                } catch (Exception e) {
                    start = -2000;
                }
                try {
                    end = b.intValue();
                } catch (Exception e) {
                    end = 2100;
                }

                HistoricalPeriod period = new HistoricalPeriod(name, start, end);
                for (int j = 0; j < arr.size(); j++) {
                    JSONObject emp = (JSONObject) arr.get(j);
                    String ThuyHieu = (String) emp.get("Thụy hiệu");
                    String MieuHieu = (String) emp.get("Miếu hiệu");
                    String TriVi = (String) emp.get("Trị vì");
                    String TheThu = (String) emp.get("Thế thứ");
                    String NienHieu = (String) emp.get("Niên hiệu");
                    String Vua = (String) emp.get("Vua");
                    String name1 = (String) emp.get("Tên húy");
                    String HoangDe = (String) emp.get("Hoàng đế");
                    String[] name2 = name1.split(",\n");
                    // String TenHuy = (String) emp.get("Tên húy");
                    List<String> TenHuy = new ArrayList<String>();

                    for (int k = 0; k < name2.length; k++) {
                        TenHuy.add(name2[k]);
                    }

                    if (ThuyHieu == null)
                        ThuyHieu = " ";
                    King king = new King(ThuyHieu, NienHieu, MieuHieu, 0, 0, TheThu, HoangDe, TenHuy);
                    kings.add(king);
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        for (int i = 0; i < figures.size(); i++) {
            String name = figures.get(i).getName();
            for (int j = 0; j < kings.size(); j++) {
                if (kings.get(j).getTenHuy().contains(name)) {
                    HistoricalFigure fi = figures.get(i);
                    King king = (King) fi;
                    king.setThuyHieu(kings.get(j).getThuyHieu());
                    king.setMieuHieu(kings.get(j).getMieuHieu());
                    king.setNienHieu(kings.get(j).getNienHieu());
                    king.setTheThu(kings.get(j).getTheThu());
                    king.setHoangDe(kings.get(j).getHoangDe());
                    king.setTriVi(0, 0);
                    king.setRoleTrue();
                    figures.remove(i);
                    figures.add(i, king);
                    break;
                }
            }
        }
        return figures;
    }
}
