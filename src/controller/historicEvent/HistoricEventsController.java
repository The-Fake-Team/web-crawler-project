package controller.historicEvent;

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
import javafx.scene.layout.VBox;
import models.historicEvent.HistoricEvent;

public class HistoricEventsController implements Initializable{

    @FXML
    private AnchorPane contentArea;

    @FXML
    private TextField eventFilter;

    @FXML
    private Label eventPageTitle;

    @FXML
    private GridPane eventContainer;
    
    private List<HistoricEvent> historicEvents = new ArrayList<HistoricEvent>();
    
    public HistoricEventsController(){
        super();
    }
    
	public HistoricEventsController(List<HistoricEvent> historicEvents) {
		super();
		this.historicEvents = historicEvents;
	}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		createContent(this.historicEvents);
    	
    	eventFilter.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				filterEventWithName(newValue);
			}
		});
	}
	
	private void filterEventWithName (String name) {
		
		List<HistoricEvent> returnList = new ArrayList<HistoricEvent>();
		
		for (int i = 0; i < this.historicEvents.size(); i ++) {
			
			if(this.historicEvents.get(i).getName().contains(name)) {
				
				returnList.add(this.historicEvents.get(i));
			}
		}
		
		eventContainer.getChildren().clear();
		createContent(returnList);
	}
	
	private void createContent(List<HistoricEvent> historicEvents) {
		
		int row = 1;
    	
    	try {
    		
	    	for (HistoricEvent event : historicEvents) {
	    		
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	    		
	    		fxmlLoader.setLocation(getClass().getResource("/screen/historicEvent/individualHistoricEvent.fxml"));
	    		
	    		VBox eventBox = fxmlLoader.load();
	    		
	    		HistoricEventController eventController = fxmlLoader.getController();
	    		
	    		eventController.setData(event);
	    		
	    		eventContainer.add(eventBox, 0, row++);
	    		eventContainer.setMargin(eventBox, new Insets(10, 10, 10, 10));
	    	}
	    	
    	} catch (IOException e) {
    		
        	e.printStackTrace();
    	}
	}

}
