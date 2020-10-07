package database;

import model.Word;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class HistoryDB extends Table {
    public HistoryDB() {
        table = "histories";
    }

    @Override
    public void addWord(Word word) {
        String explain = getExplain(word.getWord_target());
        if (!explain.equals("")) {
            deleteWord(word.getWord_target());
        }
        super.addWord(word);
    }

    public void deleteWord(String tager) {
        String sql = "DELETE FROM " + table + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tager);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public ArrayList<String> getWordFromHistoryDB() {
        String sql = "SELECT " + Database.COLUME_WORD_TARGET + " FROM " + table;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                String string = rs.getString(Database.COLUME_WORD_TARGET);
                arrayList.add(string);
//                System.out.println(string);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
//        Collections.reverse(arrayList);
        return arrayList;
    }

}
