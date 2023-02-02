package webCrawler;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.festival.Festival;

public class WebCrawler extends Application{
	
	 double x,y = 0;
	    
	 @Override
	 public void start(Stage primaryStage) throws Exception{
	    		    	
	        Parent root = FXMLLoader.load(getClass().getResource("../screen/dashboard/dashboard.fxml"));
	        primaryStage.initStyle(StageStyle.UNDECORATED);
	        
	        root.setOnMousePressed(event -> {
	            x = event.getSceneX();
	            y = event.getSceneY();
	        });

	        //move around here
	        root.setOnMouseDragged(event -> {
	            primaryStage.setX(event.getScreenX() - x);
	            primaryStage.setY(event.getScreenY() - y);
	        });
	        
	        primaryStage.setScene(new Scene(root, 1366, 768));
	        primaryStage.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void stop() throws Exception {
	    }

	    @Override
	    public void init() throws Exception {
	    }
	    
	    public void retrieveFestivalDataFromJSONFile() {
	    	
	    	 try (FileReader reader = new FileReader("../data/festival.json")){ 
	    		 
	    		 JSONParser jsonParser = new JSONParser();
	    		 
	    		 Object obj = jsonParser.parse(reader);
	         
	    		 JSONArray festivals = (JSONArray) obj;

	    		 for (int i = 0; i < festivals.size(); i++) {   
	    			 
	    			 JSONObject festivalObject = (JSONObject) festivals.get(i);
	    			 String name = (String) festivalObject.get("name"); // Get Name
	    			 String time = (String) festivalObject.get("LunarDate"); // Get Time
	    			 String place = (String) festivalObject.get("place");
	    			 String firstHeld = (String) festivalObject.get("firstHeld");

	    			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
	    			 Date date = new Date();
	    			 
	    			 try {
	    				 
	    				 date = formatter.parse(time);
	    				 
	    			 } catch (Exception e) {
	    				 
	    			 }
	    			  
	    			 Festival festival = new Festival(name, date, place, firstHeld);


	    		      String ReLatedFigures = (String) empObject.get("relatedCharacters");
	    		      
	    		      try {
	    		        
	    		    	 String[] str = ReLatedFigures.split(", ");
	    		    	 
	    		         for (int j = 0; j < str.length; j++) {
	    		         if (figureMap.get(str[j]) != null) { // Nếu có trong list nhân vật lịch sử
	    		         HistoricalFigure figure = figureMap.get(str[j]);
	    		         festival.addRelatedFigure(figure);
	    		         } else {
	    		         festival.addRelatedFigure(str[j]);
	    		         }
	    		         }
	    		         } catch (Exception e) {
	    		         // System.out.println("null");
	    		         }

	    		        // ArrayList<T> a = festival.getRelatedFigures();
	    		        // for (int j = 0; j < a.size(); j++) {
	    		        // if (a.get(j) instanceof HistoricalFigure) {
	    		        // HistoricalFigure figure = (HistoricalFigure) a.get(j);
	    		        // //System.out.println(figure.getName());
	    		        // figure.show();
	    		        // } else {
	    		        // System.out.println(a.get(j));
	    		        // }
	    		        // }
	    		        // }

	    		        // } catch (FileNotFoundException e) {
	    		        // e.printStackTrace();
	    		        // } catch (IOException e) {
	    		        // e.printStackTrace();
	    		        // }
	    }
	} 
}
