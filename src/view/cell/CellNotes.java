package view.cell;

import helper.DictionaryDAO;
import helper.ShowText;
import helper.UpdateListview;
import view.NotesPane;

public class CellNotes extends Cell {
    public CellNotes() {
        button.setStyle("-fx-background-image: url('/view/image/noted.png');");
    }

    @Override
    protected void actionHBoxClick() {
        super.actionHBoxClick();
        //click thi show
        ShowText showText = new NotesPane();
        showText.showTarget(lastItem);
    }

    @Override
    protected void actionButtonClick() {
        super.actionButtonClick();
        //click thi del
        DictionaryDAO dictionaryDAO = new DictionaryDAO();
        dictionaryDAO.deleteWord(lastItem, true);
        UpdateListview updateListview = new NotesPane();
        updateListview.removeItemInListView(lastItem);
    }
}
