package controller.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.festival.Festival;
import models.historicEvent.HistoricEvent;
import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.historicalSite.HistoricalSite;
import models.king.King;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.festival.FestivalsController;
import controller.historicEvent.HistoricEventsController;
import controller.historicalFigure.HistoricalFigureController;
import controller.historicalPeriod.HistoricalPeriodController;
import controller.historicalPeriod.HistoricalPeriodsController;
import controller.historicalSite.HistoricalSitesController;
import controller.king.KingController;
import data.FestivalData;
import data.HistoricEventData;
import data.HistoricalFigureData;
import data.HistoricalPeriodData;
import data.HistoricalSiteData;

public class DashBoardController implements Initializable {
	
	
	private List<Festival> festivals;
	private List<HistoricEvent> historicEvents;
	private List<HistoricalFigure> historicalFigures;
	private List<HistoricalPeriod> periods;
	private List<HistoricalSite> historicalSites;
	private List<King> kings;

    @FXML
    private ImageView Exit;

    @FXML
    private ImageView Minimize;

    @FXML
    private ImageView Maximize;

    @FXML
    private BorderPane window;

    @FXML
    private StackPane contentArea;
    
    @FXML
    private GridPane periodContainer;
    
    Stage stage = null;

    public DashBoardController(List<Festival> festivals, List<HistoricEvent> historicEvents, List<HistoricalFigure> historicalFigures, List<HistoricalPeriod> periods, List<HistoricalSite> historicalSites, List<King> kings) {
    	this.festivals = festivals;
    	this.periods = periods;
    	this.historicEvents = historicEvents;
    	this.historicalSites = historicalSites;
    	this.historicalFigures = historicalFigures;
    	this.kings = kings;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	createPeriodBar();
         
        Exit.setOnMouseClicked(e -> {

            System.exit(0);

        });
        Minimize.setOnMouseClicked(e -> {
            stage = (Stage) window.getScene().getWindow();
            stage.setIconified(true);
        });
        Maximize.setOnMouseClicked(e -> {
            stage = (Stage) window.getScene().getWindow();
            if(stage.isMaximized())
                stage.setMaximized(false);
            else
                stage.setMaximized(true);
        });

        

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/screen/homePage/homePage.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createPeriodBar () {
    	
    	HistoricalPeriodData historicalPeriodData = new HistoricalPeriodData("src\\data\\period.json");
		
    	ObservableList<HistoricalPeriod> historicalPeriods = FXCollections.observableArrayList(historicalPeriodData.readData());

    	int row = 0;
    	int column = 2;
    	
    	try {
    		
	    	for (HistoricalPeriod period : historicalPeriods) {
	    		
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	    		
	    		fxmlLoader.setLocation(getClass().getResource("/screen/historicalPeriod/historicalPeriod.fxml"));
	    		
	    		VBox periodBox = fxmlLoader.load();
	    		
	    		HistoricalPeriodController HistoricalPeriodController = fxmlLoader.getController();
	    		
	    		if (column == 3) {
	    			
	    			column = 0;
	    			++row;
	    		}
	    		
	    		HistoricalPeriodController.setData(period);
	    		
	    		periodContainer.add(periodBox, column ++, row);
	    		periodContainer.setMargin(periodBox, new Insets(2));
	    	}
    	} catch (IOException e) {
    	
    	}
    }
    
    @FXML
    private void homePage(javafx.event.ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/screen/homePage/homePage.fxml"));
        
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }
    
    @FXML
    private void festivals(javafx.event.ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/festival/festivalList.fxml"));    	
    	FestivalsController festivalsController = new FestivalsController(this.festivals);
    	loader.setController(festivalsController);
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }

    @FXML
    private void historicEvents(javafx.event.ActionEvent event) throws IOException {
    	
      	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/historicEvent/historicEventList.fxml"));    	
    	HistoricEventsController historicEventController = new HistoricEventsController(this.historicEvents);
    	loader.setController(historicEventController);
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    private void historicalFigures(javafx.event.ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/historicalFigure/historicalFigures.fxml"));    	
    	HistoricalFigureController historicalFiguresController = new HistoricalFigureController(this.historicalFigures);
    	loader.setController(historicalFiguresController);
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    private void historicalSites(javafx.event.ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/historicalSite/historicalSites.fxml"));    	
    	HistoricalSitesController historicalSitesController = new HistoricalSitesController(this.historicalSites);
    	loader.setController(historicalSitesController);
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    @FXML
    private void periods(javafx.event.ActionEvent event) throws IOException {
  
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/historicalPeriod/historicalPeriodList.fxml"));    	
    	HistoricalPeriodsController historicalPeriodsController = new HistoricalPeriodsController(this.periods);
    	loader.setController(historicalPeriodsController);
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    
    @FXML
    private void kings(javafx.event.ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/screen/king/king.fxml"));    	
    	KingController kingController = new KingController(this.kings);
    	loader.setController(kingController);
        Parent fxml = loader.load();
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);

    }

}
