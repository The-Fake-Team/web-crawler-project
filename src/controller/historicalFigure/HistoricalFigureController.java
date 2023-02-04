package controller.historicalFigure;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import data.HistoricalFigureData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.historicalSite.HistoricalSite;

public class HistoricalFigureController implements Initializable {

	@FXML
	private TableView<HistoricalFigure> table;
	
	@FXML
	private TableColumn<HistoricalFigure, Integer> idColumn;
	
	@FXML
	private TableColumn<HistoricalFigure, String> nameColumn;
	
	@FXML
	private TableColumn<HistoricalFigure, String> periodsColumn;
	
	@FXML
	private TableColumn<HistoricalFigure, String> placeColumn;
	
	
	private ObservableList<HistoricalFigure> historicalFigureList;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		

        HistoricalFigureData historicalFigureData = new HistoricalFigureData("src//data//historicalFigure.json");
        historicalFigureList = FXCollections.observableArrayList(historicalFigureData.readData());
                
    	idColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("name"));
		periodsColumn.setCellValueFactory(cd -> {
			try {
				
				String allPeriod = "" ;
				int i = 0;
				
				while (i < cd.getValue().getPeriods().size()) {
					
					if (i == cd.getValue().getPeriods().size() - 1) {						
						allPeriod += cd.getValue().getPeriods().get(i).getNationalName();
					} else {
						allPeriod += cd.getValue().getPeriods().get(i).getNationalName() + ", ";
					}
					i++;
				}
					
				
				return new SimpleStringProperty(allPeriod);
					
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		});
		placeColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("place"));
		
		table.setItems(historicalFigureList);
        
	}

}
