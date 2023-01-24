package crawl.nhanvat;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StandardizedEra {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("nhan_vat.json")) {
			JSONArray nhanvatList = new JSONArray();
			
			String[] allTimeStamp = {"2000 - 258 trCN", "257- 208 trCN", "207 trCN - 39", "40-43", "43-542",
									 "544-602", "603-939", "905 - 938", "939-965", "968-980", "980-1009",
									 "1010-1225", "1225-1400", "1400-1407", "1407-1413", "1414-1427", "1428-1527",
									 "1527-1592", "1533-1788", "1778-1802", "1802-1883", "1883-1945", "Nước Việt Nam mới"};
			String[] allTimeStampNormalize = {"-2000 - -258", "-257 - -208", "-207 - 39", "40 - 43", "43 - 542",
											 "544 - 602", "603 - 939", "905 - 938", "939 - 965", "968 - 980", "980 - 1009",
											 "1010 - 1225", "1225 - 1400", "1400 - 1407", "1407 - 1413", "1414 - 1427", "1428 - 1527",
											 "1527 - 1592", "1533 - 1788", "1778 - 1802", "1802 - 1883", "1883 - 1945", "1945 - 2023"};
			
			Object obj = jsonParser.parse(reader);
			
			JSONArray characterList = (JSONArray) obj;

//			ArrayList<String> arrayTimeStamp = new ArrayList<>();
            for (int i = 0; i < characterList.size(); i++) {
		        if (characterList.get(i) instanceof JSONObject) {
		        	ArrayList<String> arrayTimeStamp = new ArrayList<>();
		        	JSONObject o = (JSONObject) characterList.get(i);
		        	System.out.println(i);
		        	JSONObject o2 = (JSONObject) o.get("thông tin nhân vật");
		        	String thoi_ki = (String) o2.get("Thời kì");
		        	
//		        	arrayTimeStamp.removeAll(arrayTimeStamp);
		        	for (int j = 0; j < allTimeStamp.length; j++) {
		        		if (thoi_ki.contains(allTimeStamp[j])) {
		        			arrayTimeStamp.add(allTimeStampNormalize[j]);
		        		}
		        	}
//		        	System.out.println(arrayTimeStamp);
		        	o2.put("normalized era", arrayTimeStamp);
//		        	System.out.println(o2);
		        	nhanvatList.add(o2);
//		        	System.out.println(nhanvatList);
		        }
            }
//            System.out.println(nhanvatList);
            FileWriter file = new FileWriter("nhan_vat_2.json");
            file.write(nhanvatList.toString());
            
            file.close();
			
			
		} catch (Exception e) {
            e.printStackTrace();
        }

	}

}
