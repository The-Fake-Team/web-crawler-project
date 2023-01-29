package webCrawler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    double x,y = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	System.out.println();
    	
        Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard/dashboard.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        //move around here
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
    }

    @Override
    public void init() throws Exception {
    }
}
