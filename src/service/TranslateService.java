package service;

import controller.Controller;
import helper.ResultTranslator;
import helper.Translator;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import model.Word;

public class TranslateService extends Service<String> {
    private String text;
    private String res;
    public TranslateService(String text) {
        this.text = text;
        setOnSucceeded(workerStateEvent -> {
            //show explain
            if (text.equals("!@#firstcallapi")) {
                return;
            }
            if (!res.equals("")) {
                ResultTranslator resultTranslator = (ResultTranslator) new Controller();
                resultTranslator.Result(new Word(text,res));
            }
        });
    }
    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() {
                res = Translator.translate(text);
                return res ;
            }
        };
    }
}
