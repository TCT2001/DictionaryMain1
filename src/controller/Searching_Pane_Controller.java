package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import mainthread.FXML_Loader;
import database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private HistoryDB historyDB = new HistoryDB();

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
    private Button noteButton;
    @FXML
    private Button aboutUsButton;
    @FXML
    private Button note;
    @FXML
    private Button edit;
    @FXML
    private ListView<String> listViewHistory;
    private ObservableList observableList = FXCollections.observableArrayList();

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
        LoadHistoryWord();
        speechButton.setVisible(false);
        note.setVisible(false);
        edit.setVisible(false);
        txtWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue.compareTo(newValue) != 0) {
                String explain = dictionanryDB.getExplain(txtWord.getText());
                if (explain.compareTo("") == 0 || newValue.compareTo("") == 0) {
                    txtTarget.setText("");
                    txtDefinition.setText("");
                    speechButton.setVisible(false);
                    note.setVisible(false);
                    edit.setVisible(false);

                } else {
                    txtTarget.setText(txtWord.getText());
                    txtDefinition.setText(explain);
                    //add thoi
                    speechButton.setVisible(true);
                    if (notesDB.getExplain(txtTarget.getText()).compareTo("") == 0) {
                        note.setVisible(true);
                    } else note.setVisible(false);
                    edit.setVisible(true);
                }
            }
        });

    }

    @FXML
    public void clickNote(ActionEvent actionEvent) {
        notesDB.addWord(new Word(txtTarget.getText(), txtDefinition.getText()));
        note.setVisible(false);
    }

    @FXML
    public void clickEdit(ActionEvent actionEvent) {
        System.out.println("clicker edit");
    }

    @FXML
    public void clickDelete(ActionEvent actionEvent) {
        //TODO
    }

    public void txtWordEnter(ActionEvent actionEvent) {
        String keyword = txtWord.getText();
        if (keyword.equals("")) {
            txtDefinition.setText("");
            return;
        }
        String explain = dictionanryDB.getExplain(keyword);
        if (explain.compareTo("") == 0) {
            //dung api network thi dung multithread
            explain = Translator.translate(keyword);
            //dung api search tu
            if (explain.equals("")) {
                txtTarget.setText("Network Error !");
                return;
            }
            dictionanryDB.addWord(new Word(keyword, explain));
        }
        txtTarget.setText(keyword);
        // txtWord.setText("");
        txtDefinition.setText(explain);
        //add thoi
        historyDB.addWord(new Word(keyword, explain));
        speechButton.setVisible(true);
//        editButton.setVisible(true);
        if (!notesDB.isExist(keyword)) {
            noteButton.setVisible(true);
        }
    }

    //Load History Word Search
    private void LoadHistoryWord() {
        observableList.removeAll();
        observableList.addAll(historyDB.getWordFromHistoryDB());
        listViewHistory.getItems().addAll(observableList);
        listViewHistory.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new Searching_Pane_Controller.Cell();
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
            //Khi nhan vao chu
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
                //TODO
                txtWord.setText(lastItem);
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
    //Click chon history words
}
