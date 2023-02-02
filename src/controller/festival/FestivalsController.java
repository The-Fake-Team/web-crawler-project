package controller.festival;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class FestivalsController implements Initializable{


    @FXML
    private StackPane contentArea;

    @FXML
    private Label festivalPageTitle;
    
    public void initialize(URL location, ResourceBundle resources) {
    	
    	
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/screen/festival/festival.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
