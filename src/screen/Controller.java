package screen;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Pane Proot;

    @FXML
    private JFXTextField txtuser;

    @FXML
    private JFXTextField txtemail;

    @FXML
    private JFXPasswordField txtpass;

    @FXML
    private ImageView exit;

    double x,y=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(e -> System.exit(0));
    }

    public void Dash()throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("view/dashboard/dashboard.fxml"));

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setScene(new Scene(root));
        stage.show();
    }



}
