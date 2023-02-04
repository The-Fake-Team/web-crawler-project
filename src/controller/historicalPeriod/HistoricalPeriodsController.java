package controller.historicalPeriod;

import java.net.URL;
import java.util.ResourceBundle;

import data.HistoricalPeriodData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.historicalPeriod.HistoricalPeriod;

public class HistoricalPeriodsController implements Initializable{

	private ObservableList<HistoricalPeriod> historicalPeriods;
	
	@FXML
	private TableView<HistoricalPeriod> table;

    @FXML
    private TableColumn<HistoricalPeriod, Integer> idColumn;

    @FXML
    private TableColumn<HistoricalPeriod, String> startColumn;

    @FXML
    private TableColumn<HistoricalPeriod, String> endColumn;

    @FXML
    private TableColumn<HistoricalPeriod, String> nameColumn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		HistoricalPeriodData historicalPeriodData = new HistoricalPeriodData("src\\data\\period.json");
		
		historicalPeriods = FXCollections.observableArrayList(historicalPeriodData.readData());
		
		idColumn.setCellValueFactory(new PropertyValueFactory<HistoricalPeriod, Integer>("id"));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalPeriod, String>("name"));
		
		startColumn.setCellValueFactory(cd -> {
						
			return new SimpleStringProperty(cd.getValue().getDuration().getStart().toString());
		});
		
		
		endColumn.setCellValueFactory(cd -> {
						
			return new SimpleStringProperty(cd.getValue().getDuration().getEnd().toString());
		});
		
		table.setItems(historicalPeriods);

	}

}
