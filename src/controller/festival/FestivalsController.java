package controller.festival;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.festival.Festival;
import models.historicEvent.HistoricEvent;

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
    	
    	createContent(this.festivals);
    	
    	festivalFilter.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				filterFestivalWithName(newValue);
			}
		});
    }
    
    private void filterFestivalWithName (String name) {
		
		List<Festival> returnList = new ArrayList<Festival>();
		
		for (int i = 0; i < this.festivals.size(); i ++) {
			
			if(this.festivals.get(i).getName().contains(name)) {
				
				returnList.add(this.festivals.get(i));
			}
		}
		
		festivalContainer.getChildren().clear();
		createContent(returnList);
	}
    
    private void createContent(List<Festival> festivals) {

   		int row = 1;
   		int column = 0;

   		try {
   		
	    	for (Festival festival : festivals) {
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
   	
   			e.printStackTrace();
   		}
   }
}
