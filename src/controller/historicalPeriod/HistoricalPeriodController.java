package controller.historicalPeriod;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import models.historicalPeriod.HistoricalPeriod;

public class HistoricalPeriodController {
	
    @FXML
    private JFXButton name;

	public void setData(HistoricalPeriod period) {
		name.setText(period.getNationalName());
	}
}
