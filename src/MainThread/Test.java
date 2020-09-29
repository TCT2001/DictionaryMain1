package MainThread;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class Test {
    private Pane view;

    public Pane getPane(String fileName) throws IOException {
        URL url = Main.class.getResource("../UIDesign/"+fileName+".fxml");
        view = new FXMLLoader().load(url);
        return view;
    }
}
