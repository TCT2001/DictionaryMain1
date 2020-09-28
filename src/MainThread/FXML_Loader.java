package MainThread;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class FXML_Loader {

    public Scene getScene(String fileName) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../UIDesign/" + fileName + ".fxml"));
        return new Scene(root, 750, 500);
    }
}
