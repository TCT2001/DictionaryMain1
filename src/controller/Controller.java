package controller;

import helper.*;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Word;
import view.SearchingPane;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Controller {
    //Create a controller to connect event to view
    private static Controller controller = null;
    //Create a dictionaryDAO to manipulation to databases
    private final DictionaryDAO dictionaryDAO = new DictionaryDAO();

    /**
     *To get controller when having a event.
     */
    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    /**
     * auto complete word hint.
     * use function getWordHint(): List
     * @param text String
     */
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

    /**
     * Show definition using getExplain(): String.
     * @param text String target
     */
    public void showExplainWord(String text) {
        ShowText showText = new SearchingPane();
        String explain = dictionaryDAO.getExplain(text);
        if (!explain.equals("")) {
            showText.showExplain(explain);
        } else {
            showText.hideExplain();
        }
    }

    /**
     * if word is in database, invoke getExplain, else using translate API.
     * require Internet
     * @param text String
     */
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
                            //Platform.runLater(new Runnable() {
                            Platform.runLater(() -> {
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

    /**
     * when clicking voice button, invoke Speech.speak() to speak text.
     * @param text String
     */
    public void speak(String text) {
        Speech.speak(text);
    }

    /**
     * When clicking note button, invoke addWord() to add word to note database.
     * @param text String
     */
    public void addNotes(String text) {
        dictionaryDAO.addWord(text, true);
    }

    /**
     * when clicking edit button, invoke updateExplain to change explain.
     * column in dictionary database
     * @param target String
     * @param explain String
     */
    public void updateWord(String target, String explain) {
        if (!explain.equals("")) {
            dictionaryDAO.updateExplain(target, explain);
        }
    }

    /**
     *Show history searchs in listView.
     */
    public List<String> getListHistory() {
        return dictionaryDAO.getAllWord(false);
    }
    /**
     *Show notes words in listView.
     */
    public List<String> getListNotes() {
        return dictionaryDAO.getAllWord(true);
    }

    /**
     *get definition of word when clicking a word in list.
     */
    public String getExplain(String target) {
        return dictionaryDAO.getExplain(target);
    }

    /**
     *Check if having a noted word.
     */
    public boolean hasNotes(String word) {
        return dictionaryDAO.isExists(word, true);
    }

    public void removeNotes(String word) {
        dictionaryDAO.deleteWord(word,true);
    }

}
