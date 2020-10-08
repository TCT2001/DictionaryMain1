package helper;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.Main;

import java.io.IOException;
import java.net.URL;



public class FXML_Loader {
    private Pane view;

    public Pane getPane(String fileName) {
        URL url = Main.class.getResource("../view/fxml/" + fileName + ".fxml");
        try {
            view = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

}
