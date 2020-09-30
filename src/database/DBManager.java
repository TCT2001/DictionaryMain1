package database;

import model.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//database https://www.sqlitetutorial.net/sqlite-java/

public class DBManager {
    private static Connection connection = Database.getDatabase();

    public static void addWord(Word word) {
        if (getExplain(word.getWord_target()) == "") {
            String sql = "INSERT INTO dictionary(word_target,word_explain) VALUES(?,?)";
            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, word.getWord_target());
                pstmt.setString(2, word.getWord_explain());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

     public static String getExplain(String target) {
        String sql = "SELECT " + Database.COLUME_WORD_EXPLAIN + " FROM " +
                Database.TABLENAME + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, target);
            ResultSet rs = pstmt.executeQuery();
            if (rs.isClosed()) {
                return "";
            }
            return rs.getString(Database.COLUME_WORD_EXPLAIN);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public List<Word> getAllWord() {
        String sql = "SELECT " + Database.COLUME_WORD_TARGET + ", " + Database.COLUME_WORD_EXPLAIN + " FROM " + Database.TABLENAME;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            Word tmp;
            List<Word> res = new ArrayList<>();
            while (rs.next()) {
                tmp = new Word(rs.getString(Database.COLUME_WORD_TARGET),
                        rs.getString(Database.COLUME_WORD_EXPLAIN));
                res.add(tmp);
            }
            return res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static List<String> getAllWordHint(String text) {
        String sql = "SELECT *\n" +
                "  FROM dictionary\n" +
                " WHERE instr(word_target, '" +text +"') = 1";
        try {
            if (connection == null) {
                System.out.println("null");
                return null;
            }
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            List<String> res = new ArrayList<>();
            int count = 0;
            while (rs.next() && count <17) {
                count++;
                res.add(rs.getString(Database.COLUME_WORD_TARGET));
            }
            return res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void updateExplain(String target, String explain) {
        String sql = "UPDATE " + Database.TABLENAME + " SET " + Database.COLUME_WORD_EXPLAIN +
                " = ? " + "WHERE " + Database.COLUME_WORD_TARGET + " = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            // set the corresponding param
            pstmt.setString(1, explain);
            pstmt.setString(2, target);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteWord(String tager) {
        String sql = "DELETE FROM " + Database.TABLENAME + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tager);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
