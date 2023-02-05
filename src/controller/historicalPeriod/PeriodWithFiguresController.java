package controller.historicalPeriod;

import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import data.HistoricalFigureData;
import data.KingData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.historicalFigure.HistoricalFigure;
import models.historicalPeriod.HistoricalPeriod;
import models.king.King;

public class PeriodWithFiguresController {
	
    @FXML
    private JFXButton name;
    
    @FXML
    private TableColumn<HistoricalFigure, String> nameColumn;
    
    @FXML
    private TableColumn<HistoricalFigure, String> placeColumn;

    @FXML
    private TableColumn<HistoricalFigure, String> periodsColumn;

    @FXML
    private TableView<HistoricalFigure> table;

	public void setData(HistoricalPeriod period) throws CloneNotSupportedException {
	
		name.setText(period.getNationalName());
		
		HistoricalFigureData historicalFigureData = new HistoricalFigureData("src\\data\\historicalFigure.json");
	    List<HistoricalFigure> figures = historicalFigureData.readData();
		
	    KingData kingData = new KingData("src\\data\\king.json");
	    List<King> kings = kingData.readData();
	    figures = kingData.mergeData(figures, kings);
	    
	    List<HistoricalFigure> filterFigures = new ArrayList<HistoricalFigure>();
	    
	    for (int i = 0; i < figures.size(); i ++) {
	    	
	    	for (int j =0; j < figures.get(i).getPeriod().size(); j ++) {
	    		
	    		if (figures.get(i).getPeriod().get(j).getNationalName().equals(period.getNationalName())) {
	    			
	    			filterFigures.add(figures.get(i));
	    		}
	    	}
	    }
	    
	    nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("name"));
	    
	    placeColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("place"));
	    
	    periodsColumn.setCellValueFactory(cd -> {
	    	
	    	   try {
				     String allPeriod = "" ;
				     int i = 0;
				
				     while (i < cd.getValue().getPeriod().size()) {
					
				    	 if (i == cd.getValue().getPeriod().size() - 1) {
				    		 
				    		 allPeriod += cd.getValue().getPeriod().get(i).getNationalName();
				    		 
				    	 } else {
				    		 
						allPeriod += cd.getValue().getPeriod().get(i).getNationalName() + ", ";
					 }
					i++;
				}
					
				return new SimpleStringProperty(allPeriod);
					
			} catch (CloneNotSupportedException e) {
				
				e.printStackTrace();
			}
			return null;
		});
	    
		table.setItems(FXCollections.observableArrayList(filterFigures));
	}
}
