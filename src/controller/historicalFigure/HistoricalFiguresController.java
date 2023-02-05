package controller.historicalFigure;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.security.auth.callback.Callback;

import data.HistoricalFigureData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableView.ResizeFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
	
	@FXML
	private TableColumn<HistoricalFigure, String> descriptionColumn;
	
    @FXML
    private TextField figureFilter;

    @FXML
    private ImageView filterButton;
	
	private ObservableList<HistoricalFigure> historicalFigureList;
	
	public HistoricalFigureController(List<HistoricalFigure> historicalFigureList) {
		this.historicalFigureList = FXCollections.observableArrayList(historicalFigureList);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
                
    	idColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("name"));
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		});
		placeColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("place"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("description"));
		
		descriptionColumn.setCellFactory(param -> {
			
		        return new TableCell<HistoricalFigure, String>() {
		            @Override
		            protected void updateItem(String item, boolean empty) {
		               
		                    Text text = new Text(item);
		                    text.setStyle("-fx-text-alignment:justify;");                      
		                    text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(35));
		                    setGraphic(text);
		            }
		        };
		    });
		table.setFixedCellSize(180);
		
		
		table.setItems(this.historicalFigureList);
        
		figureFilter.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				filterFigueWithName(newValue);
			}
		});
	}
	
	private void filterFigueWithName (String name) {
		
		List<HistoricalFigure> returnList = new ArrayList<HistoricalFigure>();
		
		for (int i = 0; i < this.historicalFigureList.size(); i ++) {
			if(this.historicalFigureList.get(i).getName().contains(name)) {
				returnList.add(this.historicalFigureList.get(i));
			}
		}
		
		table.setItems(FXCollections.observableArrayList(returnList));
	}
}
