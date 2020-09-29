package MainThread;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;

public class FXML_Loader {
    private Pane view;

    public Pane getPane(String fileName) {
        URL url = Main.class.getResource("../UIDesign/" + fileName + ".fxml");
        try {
            view = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

}
