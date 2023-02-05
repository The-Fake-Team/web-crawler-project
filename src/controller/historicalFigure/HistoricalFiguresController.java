package controller.historicalFigure;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.historicalFigure.HistoricalFigure;

public class HistoricalFiguresController implements Initializable {

    @FXML
    private StackPane rootPane;
    
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
	
	public HistoricalFiguresController(List<HistoricalFigure> historicalFigureList) {
		
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
				
				e.printStackTrace();
			}
			return null;
		});
		placeColumn.setCellValueFactory(new PropertyValueFactory<HistoricalFigure, String>("place"));
		
		descriptionColumn.setCellValueFactory(cd -> { return new SimpleStringProperty("Xem thêm");});
		
		descriptionColumn.setCellFactory(cd -> {
            
			TableCell<HistoricalFigure, String> cell = new TableCell<HistoricalFigure, String>() {
			    
				final JFXButton btn = new JFXButton("Xem mô tả");
			
				@Override
				protected void updateItem(String item, boolean empty) {
					
					setGraphic(btn);
				}
			};
			
			 cell.setOnMouseClicked(event -> {

				 	FXMLLoader fxmlLoader = new FXMLLoader();
				 	
				 	fxmlLoader.setLocation(getClass().getResource("/screen/historicalFigure/historicalFigure.fxml"));
				 
				 	VBox figureBox = new VBox();
				 	
					try {
						
						figureBox = fxmlLoader.load();
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				 	HistoricalFigureController historicalFigureController = fxmlLoader.getController();
				 	historicalFigureController.setData(cell.getTableRow().getItem());
				 	
			        rootPane.getChildren().removeAll();
			        rootPane.getChildren().setAll(figureBox);
	         });
			 
			 return cell;
		});
		
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
		
		for (int i = 0; i < this.historicalFigureList.size(); i ++){
			
			if(this.historicalFigureList.get(i).getName().contains(name)){
				
				returnList.add(this.historicalFigureList.get(i));
			}
		}
		
		table.setItems(FXCollections.observableArrayList(returnList));
	}
}
