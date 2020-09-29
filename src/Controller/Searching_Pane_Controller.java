package Controller;

import MainThread.FXML_Loader;
import MainThread.Test;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Searching_Pane_Controller implements Initializable {
    @FXML
    private BorderPane BorderPaneId;
    @FXML
    private Label label;
    @FXML
    public void LoadSearchingButton(ActionEvent actionEvent) throws IOException {
        FXML_Loader loader = new FXML_Loader();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(loader.getScene("Searching_Pane"));
    }

    @FXML
    private void LoadEditingButton(ActionEvent actionEvent) throws IOException {
        Test test = new Test();
        Pane view = test.getPane("Edit_Pane");
        BorderPaneId.setCenter(view);
    }

    @FXML
    public void LoadHistoryButton(ActionEvent actionEvent) throws IOException {
        Test test = new Test();
        Pane view = test.getPane("History_Pane");
        BorderPaneId.setCenter(view);
    }

    @FXML
    public void LoadNoteButton(ActionEvent actionEvent) throws IOException {
        Test test = new Test();
        Pane view = test.getPane("Note_Pane");
        BorderPaneId.setCenter(view);
    }

    @FXML
    public void LoadAboutUsButton(ActionEvent actionEvent) throws IOException {
        Test test = new Test();
        Pane view = test.getPane("AboutUs_Pane");
        BorderPaneId.setCenter(view);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
