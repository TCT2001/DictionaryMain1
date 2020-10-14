package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    private final int width = 750;
    private final int height = 500;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/fxml/searching_pane.fxml"));
        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle("Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/view/image/appIcon.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
//        new TranslateService("hello").start();
//        new MyService().start();
    }
}
