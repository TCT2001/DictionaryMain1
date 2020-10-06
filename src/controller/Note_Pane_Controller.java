package controller;

import database.NotesDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class Note_Pane_Controller implements Initializable {
    private NotesDB notesDB = new NotesDB();
    private ObservableList observableList = FXCollections.observableArrayList();
    @FXML
    private ListView<String> listView;

    public void initialize(URL location, ResourceBundle resources) {
        LoadNoteWord();
    }

    private void LoadNoteWord() {
        observableList.removeAll();
        observableList.addAll(notesDB.getWordFromNoteDB());
        listView.getItems().addAll(observableList);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new Cell();
            }
        });
    }

    class Cell extends ListCell<String> {
        HBox hbox = new HBox(10);
        Text text = new Text();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button button = new Button();
        String lastItem;

        public Cell() {
            super();
            button.setPrefSize(25, 20);
            button.setStyle("-fx-background-image: url('/uidesign/Image/delete.png');");
            //hbox.getChildren().addAll(text, pane, button);
            hbox.getChildren().addAll(button, text, pane);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(lastItem + " : " + event);
                }
            });
//            hbox.setOnMouseMoved(mouseEvent -> {
//                System.out.println(lastItem);
//            });
            hbox.setOnMouseClicked(mouseEvent -> {
                System.out.println(lastItem);
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                text.setText(item != null ? item : "<null>");
                setGraphic(hbox);
            }
        }
    }
}