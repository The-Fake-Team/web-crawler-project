package controller.king;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
import models.historicalFigure.HistoricalFigure;
import models.king.King;

public class KingController implements Initializable{

	private ObservableList<King> kings;
	
	@FXML
    private TableColumn<King, String> thuyHieuColumn;

    @FXML
    private TableColumn<King, String> tenHuyColumn;

    @FXML
    private TableColumn<King, String> nameColumn;

    @FXML
    private TableColumn<King, String> mieuHieuColumn;

    @FXML
    private TableColumn<King, String> nienHieuColumn;

    @FXML
    private TableColumn<King, String> theThuColumn;

    @FXML
    private TableColumn<King, String> triViColumn;

    @FXML
    private TableView<King> table;
    
    @FXML
    private TextField kingFilter;

    @FXML
    private ImageView filterButton;
    
	public KingController(List<King> kings) {
		this.kings = FXCollections.observableArrayList(kings);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<King, String>("vua"));
		mieuHieuColumn.setCellValueFactory(new PropertyValueFactory<King, String>("mieuHieu"));
		thuyHieuColumn.setCellValueFactory(new PropertyValueFactory<King, String>("thuyHieu"));
		nienHieuColumn.setCellValueFactory(new PropertyValueFactory<King, String>("nienHieu"));
		theThuColumn.setCellValueFactory(new PropertyValueFactory<King, String>("theThu"));
		triViColumn.setCellValueFactory(new PropertyValueFactory<King, String>("triVi"));
		
		tenHuyColumn.setCellValueFactory(
			cd -> {
				String tenHuyString = "";
				
				for (int i = 0; i < cd.getValue().getTenHuy().size(); i ++) {
					
					if (i == cd.getValue().getTenHuy().size() - 1) {
						tenHuyString += cd.getValue().getTenHuy().get(i);
					} else {						
						tenHuyString += cd.getValue().getTenHuy().get(i) + ", ";
					}
				}
				
				return new SimpleStringProperty(tenHuyString);
			}
		);
		
		table.setItems(this.kings);

		kingFilter.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String oldValue, String newValue) {
				filterKingWithName(newValue);
			}
		});
	}
	
	private void filterKingWithName (String name) {
		
		List<King> returnList = new ArrayList<King>();
		
		for (int i = 0; i < this.kings.size(); i ++) {
			if(this.kings.get(i).getVua().contains(name)) {
				returnList.add(this.kings.get(i));
			}
		}
		
		table.setItems(FXCollections.observableArrayList(returnList));
	}

}
