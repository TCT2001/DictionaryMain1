package controller;

import helper.*;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Word;
import service.TranslateService;
import view.SearchingPane;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Controller implements ResultTranslator {
    private static Controller controller = null;
    private final static DictionaryDAO dictionaryDAO = new DictionaryDAO();

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public void suggestionsText(String text) {
        Popup popup = new SearchingPane();
        if (text == null || text.equals("")) {
            popup.hidePopup();
        } else {
            List<String> filteredEntries = dictionaryDAO.getWordHint(text.toLowerCase());
            if (!filteredEntries.isEmpty()) {
                popup.showPopup(filteredEntries, text);
            } else {
                popup.hidePopup();
            }
        }

    }

    public void showExplainWord(String text) {
        ShowText showText = new SearchingPane();
        String explain = dictionaryDAO.getExplain(text);
        if (!explain.equals("")) {
            //showPopup explain
            showText.showExplain(explain);
        } else {
            showText.hideExplain();
        }
    }

    public void translateWord(String text) {
        ShowText showText = new SearchingPane();
        UpdateListview updateListview = new SearchingPane();
        if (!dictionaryDAO.getExplain(text).equals("")) {
            //have in database
            dictionaryDAO.addWord(text, false);
            updateListview.addItemInListView(text);
        } else {
            //using api
            TranslateService translate = new TranslateService(text);
            translate.start();
//            String explain = translate.getValue();
//            if (!explain.equals("")) {
//                showText.showExplain(explain);
//                dictionaryDAO.addWord(new Word(text, explain));
//                dictionaryDAO.addWord(text, false);
//                updateListview.addItemInListView(text);
//            } else {
//                showText.showExplain("Network Error !");
//            }
        }
    }

    public void speck(String text) {
        Speech.speck(text);
    }

    public void addNotes(String text) {
        dictionaryDAO.addWord(text, true);
    }

    public void updateWord(String target, String explain) {
        if (!explain.equals("")) {
            dictionaryDAO.updateExplain(target, explain);
        }
    }

    public List<String> getListHistory() {
        return dictionaryDAO.getAllWord(false);
    }

    public List<String> getListNotes() {
        return dictionaryDAO.getAllWord(true);
    }

    public String getExplain(String target) {
        return dictionaryDAO.getExplain(target);
    }

    public boolean hasNotes(String word) {
        return dictionaryDAO.isExists(word, true);
    }

    public void removeNotes(String word) {
        dictionaryDAO.deleteWord(word,true);
    }

    @Override
    public void Result(Word word) {
        ShowText showText = new SearchingPane();
        UpdateListview updateListview = new SearchingPane();
        String explain = word.getWord_explain();
        if (!explain.equals("")) {
            showText.showExplain(explain);
            dictionaryDAO.addWord(word);
            dictionaryDAO.addWord(word.getWord_target(), false);
            updateListview.addItemInListView(word.getWord_target());
        } else {
            showText.showExplain("Network Error !");
        }
    }
}
