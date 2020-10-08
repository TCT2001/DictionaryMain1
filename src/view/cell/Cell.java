package view.cell;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//cell in listview
class Cell extends ListCell<String> {
    protected HBox hbox = new HBox(10);
    protected Text text = new Text();
    protected Label label = new Label("(empty)");
    protected Pane pane = new Pane();
    protected Button button = new Button();
    protected String lastItem;

    public Cell() {
        super();
        button.setPrefSize(25, 20);
        button.setFont(new Font(12));
        hbox.getChildren().addAll(button, text, pane);
        HBox.setHgrow(pane, Priority.ALWAYS);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                actionButtonClick();
            }
        });
        hbox.setOnMouseClicked(mouseEvent -> {
            actionHBoxClick();
        });
    }
    protected void actionHBoxClick(){}

    protected void actionButtonClick(){}

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