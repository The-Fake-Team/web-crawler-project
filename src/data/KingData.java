package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.util.ArrayList;
import java.util.List;

import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.king.King;

public class KingData extends FileInfo {
	
    public KingData(String fileName) {
        super(fileName);
    }

    public List<King> readData() {
        List<King> kings = new ArrayList<King>();
        JSONParser jsonpanser = new JSONParser();

        try (FileReader reader = new FileReader(this.getFileName())) {
            Object obj = jsonpanser.parse(reader);
            JSONArray Kings = (JSONArray) obj;
            for (int i = 0; i < Kings.size(); i++) {
                JSONObject empObject = (JSONObject) Kings.get(i);
                JSONArray arr = (JSONArray) empObject.get("kingList");

                String name = (String) empObject.get("nitionalName");

                JSONObject emp1 = (JSONObject) empObject.get("time");
                Integer start = null, end = null;
                try {
                    int a = (int) (long) emp1.get("period start");
                    start = a;
                } catch (Exception e) {
                    start = null;
                }
                try {
                    int b = (int) (long) emp1.get("period end");
                    end = b;
                } catch (Exception e) {
                    end = null;
                }

                HistoricalPeriod period = new HistoricalPeriod(name, start, end);
                for (int j = 0; j < arr.size(); j++) {
                    JSONObject emp = (JSONObject) arr.get(j);
                    String thuyHieu = (String) emp.get("Thụy hiệu");
                    String mieuHieu = (String) emp.get("Miếu hiệu");
                    String triVi = (String) emp.get("Trị vì");
                    String theThu = (String) emp.get("Thế thứ");
                    String nienHieu = (String) emp.get("Niên hiệu");
                    String vua = (String) emp.get("Vua");
                    String hoangDe = (String) emp.get("Hoàng đế");
                    String tietDoSu = (String) emp.get("Tiết độ sư");
                    String thuLinh = (String) emp.get("Thủ lĩnh");

                    String name1 = (String) emp.get("Tên húy");
                    String[] name2 = name1.split(",\n");
                    List<String> tenHuy = new ArrayList<String>();
                    for (int k = 0; k < name2.length; k++) {
                        tenHuy.add(name2[k]);
                    }

                    
                    King king = new King(vua, thuyHieu, nienHieu, tietDoSu, thuLinh, mieuHieu, triVi, theThu, hoangDe, tenHuy);
                    king.addPeriod(period);
                    kings.add(king);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return kings;
    }

    public List<HistoricalFigure> mergeData(List<HistoricalFigure> figures, List<King> kings) {
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
                    king.setTriVi(kings.get(j).getTriVi());
                    king.setHoangDe(kings.get(j).getHoangDe());
                    king.setThuLinh(kings.get(j).getThuLinh());
                    king.setTietDoSu(kings.get(j).getTietDoSu());
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
