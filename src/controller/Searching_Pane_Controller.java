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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Searching_Pane_Controller implements Initializable {
    private DictionanryDB dictionanryDB = new DictionanryDB();
    private NotesDB notesDB = new NotesDB();
    private HistoryDB historyDB = new HistoryDB();
    private List<String> listHistory = new ArrayList<>();

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
    ObservableList observableList = FXCollections.observableArrayList();

    //edit
    @FXML
    private Button btnSave;
    @FXML
    private TextArea txtEdit;

    public Searching_Pane_Controller() {
    }


    @FXML
    public void Speck(ActionEvent actionEvent) {
        Speech.speck(txtWord.getText());
    }

    @FXML
    public void loadSearchingButton(ActionEvent actionEvent) throws IOException {
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
    public void loadNoteButton(ActionEvent actionEvent) {
        FXML_Loader test = new FXML_Loader();
        Pane view = test.getPane("Note_Pane");
        BorderPaneId.setCenter(view);
    }

    @FXML
    public void loadAboutUsButton(ActionEvent actionEvent) {
        FXML_Loader test = new FXML_Loader();
        Pane view = test.getPane("AboutUs_Pane");
        BorderPaneId.setCenter(view);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadHistoryWord();
        speechButton.setVisible(false);
        note.setVisible(false);
        edit.setVisible(false);
        txtWord.setFocusTraversable(false);
        txtWord.setPromptText("Enter English Word"); //to set the hint text
        txtWord.getParent().requestFocus();
        txtWord.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.compareTo("") == 0) {
                listViewHistory.setVisible(true);
            } else {
                listViewHistory.setVisible(false);
            }

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
        btnSave.setVisible(true);
        txtDefinition.setVisible(false);
        txtEdit.setVisible(true);
        txtEdit.setText(txtDefinition.getText());
        System.out.println("clicker edit");
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
        loadHistoryWord();
    }

    //Load History Word Search
    private void loadHistoryWord() {

        observableList.removeAll(listHistory);
        listHistory = historyDB.getWordFromHistoryDB();
        observableList.addAll(listHistory);
        listViewHistory.setItems(observableList);
        listViewHistory.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new Cell();
            }
        });
        listViewHistory.setVisible(false);
        listViewHistory.refresh();
        listViewHistory.setVisible(true);
    }

    public void onSave(ActionEvent actionEvent) {
        System.out.println("?");
        String text = txtEdit.getText();
        if (!text.equals("")) {
            dictionanryDB.updateExplain(txtTarget.getText(),text);
            txtDefinition.setText(text);
        }
        btnSave.setVisible(false);
        txtEdit.setVisible(false);

        txtDefinition.setVisible(true);

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
                    historyDB.deleteWord(lastItem);
                    observableList.remove(lastItem);
                    listViewHistory.setItems(null);
                    listViewHistory.setItems(observableList);
                }
            });

            hbox.setOnMouseClicked(mouseEvent -> {
                txtWord.setText(lastItem);
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
