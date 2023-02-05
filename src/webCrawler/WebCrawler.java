package webCrawler;

import java.util.List;

import controller.dashboard.DashBoardController;
import controller.festival.FestivalsController;
import crawler.Crawler;
import data.FestivalData;
import data.HistoricEventData;
import data.HistoricalFigureData;
import data.HistoricalPeriodData;
import data.HistoricalSiteData;
import data.KingData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.festival.Festival;
import models.historicEvent.HistoricEvent;
import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.historicalSite.HistoricalSite;
import models.king.King;

public class WebCrawler extends Application{
	
	
	public static void crawlData () {
		Crawler crawler = new Crawler();
		
		Thread t = new Thread(crawler);
		t.start();
	}
	
	 double x,y = 0;
	    
	 @Override
	 public void start(Stage primaryStage) throws Exception{
		 	
//
	      	HistoricalPeriodData historicalPeriodData = new HistoricalPeriodData("src\\data\\period.json");
	        List<HistoricalPeriod> periods = historicalPeriodData.readData();

	        HistoricalFigureData historicalFigureData = new HistoricalFigureData("src\\data\\historicalFigure.json");
	        List<HistoricalFigure> figures = historicalFigureData.readData();

	        HistoricEventData historicEventData = new HistoricEventData("src\\data\\historicalEvent.json");
	        List<HistoricEvent> events = historicEventData.readData(figures);

	        KingData kingData = new KingData("src\\data\\king.json");
	        List<King> kings = kingData.readData();
	        figures = kingData.mergeData(figures, kings);

	        FestivalData festivalData = new FestivalData("src\\data\\festival.json");
	        List<Festival> festivals = festivalData.readData(figures);

	        HistoricalSiteData historicalSiteData = new HistoricalSiteData("src\\data\\historicalSite.json");
	        List<HistoricalSite> sites = historicalSiteData.readData();
	        
		 	FXMLLoader loader = new FXMLLoader(getClass().getResource("../screen/dashboard/dashboard.fxml"));
		 	
	    	DashBoardController dashBoardController = new DashBoardController(festivals, events, figures, periods, sites, kings);
	    	
	    	loader.setController(dashBoardController);
	        Parent root = loader.load();
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
//	    	crawlData();

	        launch(args);
	    }

	    @Override
	    public void stop() throws Exception {
	    }

	    @Override
	    public void init() throws Exception {
	    }
	    
	   
}
