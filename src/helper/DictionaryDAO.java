package helper;

import database.Database;
import model.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDAO {
    private final Connection connection = Database.getDatabase();

    //dictionary table

    /**
     * add word to dictionary table
     *
     * @param word Word
     */
    public void addWord(Word word) {
        String target = word.getWord_target().toLowerCase();
        String explain = word.getWord_explain();
        if (target.equals("") || explain.equals("")) {
            return;
        }
        String expdb = getExplain(target);
        if (expdb.equals("")) {
            String sql = "INSERT INTO dictionary(word_target,word_explain) VALUES(?,?)";
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, target);
                pstmt.setString(2, explain);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * get explain of target in dictionary table
     *
     * @param target String
     */
    public String getExplain(String target) {
        String sql = "SELECT " + Database.COLUMN_WORD_EXPLAIN + " FROM dictionary WHERE "
                + Database.COLUMN_WORD_TARGET + " = ?";
        try {

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, target);
            ResultSet rs = pstmt.executeQuery();
            if (rs.isClosed()) {
                return "";
            }
            return rs.getString(Database.COLUMN_WORD_EXPLAIN);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * update explain using when edit
     *
     * @param target String
     */
    public void updateExplain(String target, String explain) {
        String sql = "UPDATE dictionary SET " + Database.COLUMN_WORD_EXPLAIN +
                " = ? " + "WHERE " + Database.COLUMN_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, explain);
            pstmt.setString(2, target);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * get list target word using for search text hint
     *
     * @param text String
     */
    public List<String> getWordHint(String text) {
        List<String> res = new ArrayList<>();
        if (text.contains("\'")) {
            return res;
        }
        String sql = "SELECT *\n" +
                "  FROM dictionary\n" +
                " WHERE instr(word_target, '" + text + "') = 1 LIMIT 0,13";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(Database.COLUMN_WORD_TARGET).compareTo("") != 0) {
                    res.add(rs.getString(Database.COLUMN_WORD_TARGET));
                }
            }
            return res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * add word to notes table or histories table
     *
     * @param word Word
     */
    public void addWord(String word, boolean isNotesTable) {
        if (word.equals("") || isExists(word, isNotesTable)) {
            return;
        }
        String table = isNotesTable ? "notes" : "histories";
        String sql = "INSERT INTO " + table + "(word_target) VALUES(?)";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, word);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * check word is in table
     *
     * @param word Word
     */
    public boolean isExists(String word, boolean isNotesTable) {
        String table = isNotesTable ? "notes" : "histories";
        String sql = "SELECT * FROM " +
                table + " WHERE " + Database.COLUMN_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, word);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.isClosed()) {
                    return false;
                }
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return false;
    }

    /**
     * getAll word to notes table or histories table
     */
    public List<String> getAllWord(boolean isNotesTable) {
        String table = isNotesTable ? "notes" : "histories";
        String sql = "SELECT " + Database.COLUMN_WORD_TARGET + " FROM " + table;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String string = rs.getString(Database.COLUMN_WORD_TARGET);
                arrayList.add(string);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }

    /**
     * delete word to notes table or histories table
     *
     * @param target String
     */
    public void deleteWord(String target, boolean isNotesTable) {
        String table = isNotesTable ? "notes" : "histories";
        String sql = "DELETE FROM " + table + " WHERE " + Database.COLUMN_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, target);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }
}
