package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class NotesPane implements Initializable {
    @FXML
    private ListView<String> listView;
    @FXML
    private Text textDifinitionInNote;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
