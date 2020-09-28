package Controller;

import MainThread.FXML_Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class Edit_Pane_Controller {
    @FXML
    public void LoadSearchingButton(ActionEvent actionEvent) throws IOException {
        FXML_Loader loader = new FXML_Loader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(loader.getScene("Searching_Pane"));
    }

    @FXML
    public void LoadEditingButton(ActionEvent actionEvent) throws IOException {
        FXML_Loader loader = new FXML_Loader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(loader.getScene("Edit_Pane"));
    }

    @FXML
    public void LoadHistoryButton(ActionEvent actionEvent) throws IOException {
        FXML_Loader loader = new FXML_Loader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(loader.getScene("History_Pane"));
    }

    @FXML
    public void LoadNoteButton(ActionEvent actionEvent) throws IOException {
        FXML_Loader loader = new FXML_Loader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(loader.getScene("Note_Pane"));
    }

    @FXML
    public void LoadAboutUsButton(ActionEvent actionEvent) throws IOException {
        FXML_Loader loader = new FXML_Loader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(loader.getScene("AboutUs_Pane"));
    }
}
