package controller.historicalPeriod;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import data.HistoricalPeriodData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import models.historicalPeriod.HistoricalPeriod;

public class HistoricalPeriodsController implements Initializable{

	private ObservableList<HistoricalPeriod> historicalPeriods;
	
	@FXML
	private TableView<HistoricalPeriod> table;

    @FXML
    private TableColumn<HistoricalPeriod, String> startColumn;

    @FXML
    private TableColumn<HistoricalPeriod, String> endColumn;

    @FXML
    private TableColumn<HistoricalPeriod, String> nameColumn;

    @FXML
    private ImageView filterButton;
    
    @FXML
    private TextField periodFilter;
    
    public HistoricalPeriodsController(List<HistoricalPeriod> historicalPeriods) {
    	this.historicalPeriods = FXCollections.observableArrayList(historicalPeriods);
    }
    
    public void setHistoricalPeriods(List<HistoricalPeriod> historicalPeriods) {
    	this.historicalPeriods = FXCollections.observableArrayList(historicalPeriods);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalPeriod, String>("nationalName"));
		
		startColumn.setCellValueFactory(cd -> {
			
			if (cd.getValue().getDuration().getStart() == null) {
				
				return new SimpleStringProperty("Không rõ");
			}
			
			if (cd.getValue().getDuration().getStart() < 0) {
				return new SimpleStringProperty(Integer.toString(0 - cd.getValue().getDuration().getStart()) + " TCN");
			}
			
			return new SimpleStringProperty(cd.getValue().getDuration().getStart().toString());
		});
		
		
		endColumn.setCellValueFactory(cd -> {
			
			if (cd.getValue().getDuration().getEnd() == null) {
				
				return new SimpleStringProperty("Không rõ");
			}
			
			if (cd.getValue().getDuration().getEnd() < 0) {
				return new SimpleStringProperty(Integer.toString(0 - cd.getValue().getDuration().getEnd()) + " TCN");
			}
			
			return new SimpleStringProperty(cd.getValue().getDuration().getEnd().toString());
		});
		
		table.setItems(this.historicalPeriods);
		
		periodFilter.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				filterPeriodWithName(newValue);
			}
		});
	}
	
	private void filterPeriodWithName (String name) {
		
		List<HistoricalPeriod> returnList = new ArrayList<HistoricalPeriod>();
		
		for (int i = 0; i < this.historicalPeriods.size(); i ++) {
			if(this.historicalPeriods.get(i).getNationalName().contains(name)) {
				returnList.add(this.historicalPeriods.get(i));
			}
		}
		
		table.setItems(FXCollections.observableArrayList(returnList));
	}
	
}
