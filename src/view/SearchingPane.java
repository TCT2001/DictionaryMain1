package view;

import controller.Controller;
import helper.FXML_Loader;
import helper.Popup;
import helper.ShowText;
import helper.UpdateListview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.cell.CellHistory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SearchingPane implements Popup, ShowText, UpdateListview {
    private static final ContextMenu entriesPopup = new ContextMenu();
    private static final Controller controller = Controller.getController();

    static TextField txtWord;
    static BorderPane borderPane;
    static Text txtTarget;
    static Text txtDefinition;
    static Button btnSpeak;
    static Button btnNotes;
    static Button btnAboutUs;
    static Button btnEdit;
    static ListView<String> listViewHistory;
    static Button btnSaveEdit;
    static TextArea txtEdit;
    static Button btnNotesPane;
    static Button btnAboutUsPane;
    static Button btnSearchPane;

    static void init() {
        //while input
        txtWord.focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            entriesPopup.hide();
        });
        txtWord.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            //auto complete text and showPopup text definition
            controller.suggestionsText(newvalue);
            controller.showExplainWord(newvalue);
        });
        //after input text
        txtWord.setOnAction(actionEvent -> {
            //add history or using translate api
            controller.translateWord(txtWord.getText());
        });

        //when click speck
        btnSpeak.setOnAction(actionEvent -> {
            //speck
            controller.speck(txtWord.getText());
        });

        //when click note
        btnNotes.setOnAction(actionEvent -> {
            //add action
            if (!controller.hasNotes(txtWord.getText().trim())) {
                controller.addNotes(txtWord.getText());
                btnNotes.setStyle("-fx-background-image: url('/view/image/noted.png');");
            }
        });

        //when click edit
        btnEdit.setOnAction(actionEvent -> {
            //edit
            btnSaveEdit.setVisible(true);
            txtEdit.setText(txtDefinition.getText());
            txtEdit.setVisible(true);
            txtDefinition.setVisible(false);
        });

        //after edit
        btnSaveEdit.setOnAction(actionEvent -> {
            if (!txtEdit.getText().equals("")) {
                txtDefinition.setText(txtEdit.getText());
                controller.updateWord(txtTarget.getText(), txtDefinition.getText());
            }
            btnSaveEdit.setVisible(false);
            txtEdit.setVisible(false);
            txtDefinition.setVisible(true);

        });

        //open about us screen
        btnAboutUsPane.setOnAction(actionEvent -> {
            FXML_Loader test = new FXML_Loader();
            Pane view = test.getPane("AboutUs_Pane");
            borderPane.setCenter(view);

        });

        //open note screen
        btnNotesPane.setOnAction(actionEvent -> {
            FXML_Loader test = new FXML_Loader();
            Pane view = test.getPane("Note_Pane");
            borderPane.setCenter(view);
        });

        //open screen search
        btnSearchPane.setOnAction(actionEvent -> {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(SearchingPane.class.getResource("../view/fxml/searching_pane.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root, 800, 500);
            stage.setScene(scene);
        });


        //fill listViewHistory
        fillListView();
    }

    private static void fillListView() {
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.addAll(controller.getListHistory());
        listViewHistory.setItems(observableList);
        listViewHistory.setCellFactory(stringListView -> {
            return new CellHistory();
        });
    }

    //set visible button
    private void setVisibleButton(boolean isVisible) {
        btnSpeak.setVisible(isVisible);
        btnEdit.setVisible(isVisible);
        btnNotes.setVisible(isVisible);
        if (isVisible && controller.hasNotes(txtWord.getText())) {
            btnNotes.setStyle("-fx-background-image: url('/view/image/noted.png');");
        } else {
            btnNotes.setStyle("-fx-background-image: url('/view/image/NoteTinyIcon.png');");
        }
    }

    @Override
    public void hidePopup() {
        listViewHistory.setVisible(true);
        entriesPopup.hide();
    }

    @Override
    public void showPopup(List<String> searchResult, String searchReauest) {
        listViewHistory.setVisible(false);
        List<CustomMenuItem> menuItems = new LinkedList<>();
        for (String result : searchResult) {
            Label entryLabel = new Label();
            entryLabel.setGraphic(Styles.buildTextFlow(result, searchReauest));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);
            item.setOnAction(actionEvent -> {
                txtWord.setText(result);
                txtWord.positionCaret(result.length());
                entriesPopup.hide();
            });
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
        if (!entriesPopup.isShowing() && searchResult.size() > 0) { //optional
            entriesPopup.show(txtWord, Side.BOTTOM, 24, 0); //position of popup
        }
    }


    @Override
    public void showExplain(String text) {
        txtDefinition.setText(text);
        if (text.equals("Network Error !")) {
            return;
        }
        txtTarget.setText(txtWord.getText());
        setVisibleButton(true);
        //set graphics for btnNotes
    }

    @Override
    public void showTarget(String text) {
        txtWord.setText(text);
    }

    @Override
    public void hideExplain() {
        if (!txtDefinition.getText().equals("")) {
            txtDefinition.setText("");
            txtTarget.setText("");
            setVisibleButton(false);

        }
    }


    @Override
    public void addItemInListView(String item) {
        ObservableList observableList = listViewHistory.getItems();
        observableList.add(item);
        listViewHistory.setItems(null);
        listViewHistory.setItems(observableList);
    }

    @Override
    public void removeItemInListView(String item) {
        ObservableList observableList = listViewHistory.getItems();
        observableList.remove(item);
        listViewHistory.setItems(null);
        listViewHistory.setItems(observableList);
    }
}
