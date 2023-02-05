package controller.historicalSite;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import data.HistoricalSiteData;
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
import models.historicalSite.HistoricalSite;

public class HistoricalSitesController implements Initializable{
	
	@FXML
	private TableView<HistoricalSite> table;
	
	@FXML
	private TableColumn<HistoricalSite, Integer> idColumn;
	
	@FXML
	private TableColumn<HistoricalSite, String> nameColumn;
	
	@FXML
	private TableColumn<HistoricalSite, String> recognitionDateColumn;
	
	@FXML
	private TableColumn<HistoricalSite, String> placeColumn;
	
    @FXML
    private ImageView filterButton;
    
    @FXML
    private TextField siteFilter;
    
	private ObservableList<HistoricalSite> historicalSiteList;
	
	public HistoricalSitesController(List<HistoricalSite> historicalSiteList) {
		this.historicalSiteList = FXCollections.observableArrayList(historicalSiteList);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		idColumn.setCellValueFactory(new PropertyValueFactory<HistoricalSite, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<HistoricalSite, String>("name"));
		
		recognitionDateColumn.setCellValueFactory(cd -> {
			
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Vietnam"));
			cal.setTime(cd.getValue().getRecognitionDate());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			String date = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year); 
			return new SimpleStringProperty(date);
		});
		
		placeColumn.setCellValueFactory(new PropertyValueFactory<HistoricalSite, String>("place"));
		
		table.setItems(historicalSiteList);

		siteFilter.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				filterSiteWithName(newValue);
			}
		});

	}
	
	private void filterSiteWithName (String name) {
		
		List<HistoricalSite> returnList = new ArrayList<HistoricalSite>();
		
		for (int i = 0; i < this.historicalSiteList.size(); i ++) {
			if(this.historicalSiteList.get(i).getName().contains(name)) {
				returnList.add(this.historicalSiteList.get(i));
			}
		}
		
		table.setItems(FXCollections.observableArrayList(returnList));
	}
	

}
