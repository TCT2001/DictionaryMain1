package view;

import controller.Controler;
import helper.ShowText;
import helper.UpdateListview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import view.cell.CellNotes;

import java.net.URL;
import java.util.ResourceBundle;

public class NotesPane implements Initializable, ShowText, UpdateListview {
    @FXML
    private ListView<String> lvNotes;
    @FXML
    private Text txtTarget;
    @FXML
    private Text txtDifinition;
    private Controler controler = new Controler();
    private static ListView<String> slvNotes;
    private static Text stxtTarget;
    private static Text stxtDifinition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slvNotes = lvNotes;
        stxtTarget = txtTarget;
        stxtDifinition = txtDifinition;
        fillListView();
    }

    private void fillListView() {
        ObservableList observableList = FXCollections.observableArrayList();
        observableList.addAll(controler.getListNotes());
        slvNotes.setItems(observableList);
        slvNotes.setCellFactory(stringListView -> {
            return new CellNotes();
        });
    }


    @Override
    public void showExplain(String text) {
        //TODO
    }

    @Override
    public void hideExplain() {
        //TODO
    }

    @Override
    public void showTarget(String text) {
        stxtTarget.setText(text);
        stxtDifinition.setText(controler.getExplain(text));
    }

    @Override
    public void addItemInListView(String item) {
        //TODO
    }

    @Override
    public void removeItemInListView(String item) {
        ObservableList observableList = slvNotes.getItems();
        observableList.remove(item);
        slvNotes.setItems(null);
        slvNotes.setItems(observableList);
    }
}
