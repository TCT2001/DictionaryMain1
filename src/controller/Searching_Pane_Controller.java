package controller;
import mainthread.FXML_Loader;
import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Word;
import speech.Speech;
import translateapi.Translator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Searching_Pane_Controller implements Initializable {
    private DictionanryDB dictionanryDB = new DictionanryDB();
    private NotesDB notesDB = new NotesDB();

    @FXML
    private BorderPane BorderPaneId;
    @FXML
    private Text txtTarget;
    @FXML
    private Text txtPronoune;
    @FXML
    private Text txtDefinition;
    @FXML
    private Button speechButton;
    @FXML
    private TextField txtWord;
    @FXML
    private Button searchButton;
    @FXML
    private Button editButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button noteButton;
    @FXML
    private Button aboutUsButton;
    @FXML
    private Button note;
    @FXML
    private Button edit;
    @FXML
    private Button delete;

    public Searching_Pane_Controller() {
    }


    @FXML
    public void Speck(ActionEvent actionEvent) {
        Speech.speck(txtWord.getText());
    }

    @FXML
    public void LoadSearchingButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../uidesign/Searching_Pane.fxml"));
        Scene scene = new Scene(root, 750, 500);
        stage.setScene(scene);
    }

    @FXML
    private void LoadEditingButton(ActionEvent actionEvent) {
        FXML_Loader test = new FXML_Loader();
        Pane view = test.getPane("Edit_Pane");
        BorderPaneId.setCenter(view);
    }

    @FXML
    public void LoadHistoryButton(ActionEvent actionEvent) {
        FXML_Loader test = new FXML_Loader();
        Pane view = test.getPane("History_Pane");
        BorderPaneId.setCenter(view);
    }

    @FXML
    public void LoadNoteButton(ActionEvent actionEvent) {
        FXML_Loader test = new FXML_Loader();
        Pane view = test.getPane("Note_Pane");
        BorderPaneId.setCenter(view);
    }

    @FXML
    public void LoadAboutUsButton(ActionEvent actionEvent) {
        FXML_Loader test = new FXML_Loader();
        Pane view = test.getPane("AboutUs_Pane");
        BorderPaneId.setCenter(view);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (txtTarget.getText().compareTo("") == 0) {
            speechButton.setVisible(false);
            note.setVisible(false);
            delete.setVisible(false);
            edit.setVisible(false);
        }
        txtWord.setOnAction(actionEvent -> {
            String explain = dictionanryDB.getExplain(txtWord.getText());
           if (explain.compareTo("") == 0) {
               txtTarget.setText(Translator.translate(txtWord.getText()));
               //dung api search tu
           } else {
               txtTarget.setText(txtWord.getText());
               txtWord.setText("");
               txtDefinition.setText(explain);
               //add thoi
           }
        });

        txtWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.compareTo(newValue) != 0) {
                String explain = dictionanryDB.getExplain(txtWord.getText());
                if (explain.compareTo("") == 0 || newValue.compareTo("") == 0) {
                    //dung api search tu
                    //tam thoi thi an het text di
                    txtTarget.setText("");
                    txtDefinition.setText("");
                    speechButton.setVisible(false);
                    note.setVisible(false);
                    delete.setVisible(false);
                    edit.setVisible(false);

                } else {
                    txtTarget.setText(txtWord.getText());
                    txtDefinition.setText(explain);
                    //add thoi
                    speechButton.setVisible(true);
                    if (notesDB.getExplain(txtTarget.getText()).compareTo("") == 0) {
                        note.setVisible(true);
                    } else note.setVisible(false);
                    delete.setVisible(true);
                    edit.setVisible(true);
                }
            }
        });

    }

    @FXML
    public void clickNote(ActionEvent actionEvent){
        notesDB.addWord(new Word(txtTarget.getText(),txtDefinition.getText()));
        note.setVisible(false);
    }

    @FXML
    public void clickEdit(ActionEvent actionEvent){
        //TODO
    }

    @FXML
    public void clickDelete(ActionEvent actionEvent){
        //TODO
    }

}
