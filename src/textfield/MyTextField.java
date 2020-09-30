package textfield;
import database.DBManager;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class MyTextField extends TextField {


    private final SortedSet<String> entries;
    //popup GUI
    private ContextMenu entriesPopup;

    public MyTextField() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();

        setListner();
    }
    /**
     * "Suggestion" specific listners
     */
    private void setListner() {
        //Add "suggestions" by changing text
        textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = getText();
            //always hide suggestion if nothing has been entered (only "spacebars" are dissalowed in TextFieldWithLengthLimit)
            if (enteredText == null || enteredText.isEmpty()) {
                entriesPopup.hide();
            } else {
                List<String> filteredEntries = DBManager.getAllWordHint(enteredText);
                //some suggestions are found
                if (!filteredEntries.isEmpty()) {
                    //build popup - list of "CustomMenuItem"
                    populatePopup(filteredEntries, enteredText);
                    if (!entriesPopup.isShowing()) { //optional
                        entriesPopup.show(MyTextField.this, Side.BOTTOM, 0, 0); //position of popup
                    }
                    //no suggestions -> hide
                } else {
                    entriesPopup.hide();
                }
            }
        });

        //Hide always by focus-in (optional) and out
        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            entriesPopup.hide();
        });
    }


    /**
     * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
     *
     * @param searchResult The set of matching strings.
     */
    private void populatePopup(List<String> searchResult, String searchReauest) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 17;
        int count = Math.min(searchResult.size(), maxEntries);
        //Build list as set of labels
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label();
            entryLabel.setGraphic(Styles.buildTextFlow(result, searchReauest));
            entryLabel.setPrefHeight(10);  //don't sure why it's changed with "graphic"
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            //if any suggestion is select set it into text and close popup
            item.setOnAction(actionEvent -> {
                setText(result);
                positionCaret(result.length());
                entriesPopup.hide();
            });
        }

        //"Refresh" context menu
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }


    /**
     * Get the existing set of autocomplete entries.
     *
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries() { return entries; }
}