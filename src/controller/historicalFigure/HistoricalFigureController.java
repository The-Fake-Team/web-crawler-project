package controller.historicalFigure;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import models.historicalFigure.HistoricalFigure;

public class HistoricalFigureController implements Initializable {
	
    @FXML
    private JFXButton name;

    @FXML
    private Text description;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void setData(HistoricalFigure historicalFigure) {
		name.setText(historicalFigure.getName());
		description.setText(historicalFigure.getDescription());
	}
}
