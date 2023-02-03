package crawler.utils;

import java.util.ArrayList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public final class Utils {

	public static String removeTitle(String name) {

		// Remove title of historical figures, such as "vua", "chúa",...
		String[] titlesList = { "vua", "chúa", "đại tướng", "chủ tịch", "thủ tướng" };
		for (String title : titlesList) {
			if (name.startsWith(title)) {
				name = name.replace(title, "").trim();
				break;
			}
		}
		return name;
	}

	public static String extractInt(String str) {
		// Replacing every non-digit number
		// with a space(" ")
		str = str.replaceAll("[^\\d]", " ");

		// Remove extra spaces from the beginning
		// and the ending of the string
		str = str.trim();

		// Replace all the consecutive white
		// spaces with a single space
		str = str.replaceAll(" + ", " ");

		if (str.equals(" "))
			return "-1";
		return str;
	}

	public static String findPeriodName(int start, int end, String periodNameOfKing) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader myReader = new FileReader("src\\data\\period.json");

		Object obj = jsonParser.parse(myReader);

		JSONArray periodList = (JSONArray) obj;
		for (int i = 1; i < periodList.size() - 1; i++) {
			JSONObject period = (JSONObject) periodList.get(i);
			JSONObject duration = (JSONObject) period.get("duration");
			int durationStart = ((Long) duration.get("start")).intValue();
			int durationEnd = ((Long) duration.get("end")).intValue();

			if (start >= durationStart - 1 && end <= durationEnd + 1) { // 1 year difference between different websites
				System.out.println("Change name");
				return (String) period.get("name");
			} 
		}

		JSONObject newPeriod = new JSONObject();
		JSONObject newDuration = new JSONObject();
		newDuration.put("start", start);
		newDuration.put("end", end);
		newPeriod.put("name", periodNameOfKing);
		newPeriod.put("duration", newDuration);
	
		periodList.add(newPeriod);

		FileWriter myWriter = new FileWriter("src\\data\\period.json");
		myWriter.write(periodList.toString());
		myWriter.close();

		return periodNameOfKing;
	}

	public static Hashtable<Integer, ArrayList<String>> getSimpleHistoricalFigureList()
			throws IOException, ParseException {

		Hashtable<Integer, ArrayList<String>> figureNameAndId = new Hashtable<Integer, ArrayList<String>>();

		JSONParser jsonParser = new JSONParser();
		FileReader myReader = new FileReader("src\\data\\historicalFigure.json");

		Object obj = jsonParser.parse(myReader);

		JSONArray figureList = (JSONArray) obj;

		for (int i = 0; i < figureList.size(); i++) {

			JSONObject figure = (JSONObject) figureList.get(i);
			int id = ((Long) figure.get("id")).intValue();
			figureNameAndId.put(id, new ArrayList<String>());
			figureNameAndId.get(id).add((String) figure.get("name"));
			figureNameAndId.get(id).add((String) figure.get("otherName"));
		}

		return figureNameAndId;
	}

}
