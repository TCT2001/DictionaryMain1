package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewSearchPane implements Initializable {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Text txtTarget;
    @FXML
    private Text txtDefinition;
    @FXML
    private Button btnSpeak;
    @FXML
    private TextField txtWord;
    @FXML
    private Button btnNotes;
    @FXML
    private Button btnAboutUs;
    @FXML
    private Button btnSearchPane;
    @FXML
    private Button btnEdit;
    @FXML
    private ListView<String> listViewHistory;
    @FXML
    private Button btnSaveEdit;
    @FXML
    private TextArea txtEdit;
    @FXML
    private Button btnNotesPane;
    @FXML
    private Button btnAboutUsPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initView();
        SearchingPane.init();
    }

    private void initView() {
        SearchingPane.txtWord = txtWord;
        SearchingPane.txtTarget = txtTarget;
        SearchingPane.txtDefinition = txtDefinition;
        SearchingPane.txtEdit = txtEdit;
        SearchingPane.btnNotes = btnNotes;
        SearchingPane.btnEdit = btnEdit;
        SearchingPane.btnSpeak = btnSpeak;
        SearchingPane.btnAboutUsPane = btnAboutUsPane;
        SearchingPane.btnNotesPane = btnNotesPane;
        SearchingPane.btnSaveEdit = btnSaveEdit;
        SearchingPane.borderPane = borderPane;
        SearchingPane.btnAboutUs = btnAboutUs;
        SearchingPane.listViewHistory = listViewHistory;
        SearchingPane.btnSearchPane = btnSearchPane;
    }
}
