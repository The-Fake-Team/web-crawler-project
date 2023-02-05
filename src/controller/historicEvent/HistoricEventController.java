package controller.historicEvent;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import models.historicEvent.HistoricEvent;

public class HistoricEventController implements Initializable {


    @FXML
    private JFXButton moreButton;

    @FXML
    private JFXButton name;

    @FXML
    private Text description;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void setData(HistoricEvent historicEvent) {
		
		name.setText(historicEvent.getName());
		
		description.setText(historicEvent.getDescription());
	}
}
