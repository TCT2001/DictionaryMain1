package view.cell;

import helper.DictionaryDAO;
import helper.ShowText;
import helper.UpdateListview;
import view.SearchingPane;

public class CellHistory extends Cell {
    public CellHistory() {
        button.setStyle("-fx-background-image: url('/view/image/delete.png');");
    }

    @Override
    protected void actionHBoxClick() {
        ShowText showText = new SearchingPane();
        showText.showTarget(lastItem);
    }

    @Override
    protected void actionButtonClick() {
        super.actionButtonClick();
        DictionaryDAO dictionaryDAO = new DictionaryDAO();
        dictionaryDAO.deleteWord(lastItem, false);
        UpdateListview updateListview = new SearchingPane();
        updateListview.removeItemInListView(lastItem);
    }
}
