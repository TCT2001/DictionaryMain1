package controller;

import database.NotesDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.ResourceBundle;

public class Note_Pane_Controller implements Initializable {
    NotesDB notesDB = new NotesDB();
    ObservableList observableList = FXCollections.observableArrayList();
    @FXML
    private ListView <String> listView;
    public void initialize(URL location, ResourceBundle resources) {
        LoadNoteWord();
    }
    private void LoadNoteWord(){
        observableList.removeAll();
        observableList.addAll(notesDB.getWordFromNoteDB());
        listView.getItems().addAll(observableList);
    }

    static class Cell extends ListCell<String> {
        private HBox hBox = new HBox();
        private Pane pane = new Pane();
        Button button = new Button("Del");

        public Cell() {
            super();
            hBox.getChildren().addAll(this.button);
            HBox.setHgrow(pane, Priority.ALWAYS);
        }
    }

}