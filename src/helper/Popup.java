package helper;

import java.util.List;

public interface Popup {
    void hidePopup();
    void showPopup(List<String> searchResult, String searchRequest);
}
