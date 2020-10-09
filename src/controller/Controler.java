package controller;

import helper.*;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Word;
import view.SearchingPane;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Controler {
    private static Controler controler = null;
    private DictionaryDAO dictionaryDAO = new DictionaryDAO();

    public static Controler getControler() {
        if (controler == null) {
            controler = new Controler();
        }
        return controler;
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
            Service<Void> service = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String explain = Translator.translate(text);
                                        if (!explain.equals("")) {
                                            showText.showExplain(explain);
                                            dictionaryDAO.addWord(new Word(text, explain));
                                            dictionaryDAO.addWord(text, false);
                                            updateListview.addItemInListView(text);
                                        } else {
                                            showText.showExplain("Network Error !");
                                        }
                                    } finally {
                                        latch.countDown();
                                    }
                                }
                            });
                            latch.await();
                            return null;
                        }
                    };
                }
            };
            service.start();
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

    public boolean hasNotes(String word){
        return dictionaryDAO.isExists(word,true);
    }


}
