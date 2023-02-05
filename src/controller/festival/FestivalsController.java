package controller.festival;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import data.FestivalData;
import data.HistoricalFigureData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.festival.Festival;
import models.historicalFigure.HistoricalFigure;

public class FestivalsController implements Initializable{

	private List<Festival> festivals = new ArrayList<Festival>();

	@FXML
	private Label festivalPageTitle;

    @FXML
    private AnchorPane contentArea;

    @FXML
    private GridPane festivalContainer;

    @FXML
    private TextField festivalFilter;
    
    public FestivalsController() {
    	super();
    }
    
	public FestivalsController(List<Festival> festivals) {
		
		super();
		this.festivals = festivals;
	}

    
    public void initialize(URL location, ResourceBundle resources) {
    	
    	int row = 1;
    	int column = 0;

    	try {
    		
	    	for (Festival festival : this.festivals) {
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	    		fxmlLoader.setLocation(getClass().getResource("/screen/festival/Invididualfestival.fxml"));
	    		
	    		HBox festivalBox = fxmlLoader.load();
	    		FestivalController festivalController = fxmlLoader.getController();
	    		festivalController.setData(festival);
	    		
	    		if (column == 3) {
	    			
	    			column = 0;
	    			++row;
	    		}
	    		
	    		festivalContainer.add(festivalBox, column ++, row);
	    		festivalContainer.setMargin(festivalBox, new Insets(10, 10, 10, 10));
	    	}
    	} catch (IOException e) {
    	
    	}
   }
}
