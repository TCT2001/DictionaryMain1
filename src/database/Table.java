package database;

import model.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Table {
    //CRUD
    protected Connection connection = Database.getDatabase();
    protected String table = "";
    //c
    public void addWord(Word word) {
        if (getExplain(word.getWord_target()).equals("")) {
            String sql = "INSERT INTO "+table+"(word_target,word_explain) VALUES(?,?)";
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
    //r
    public String getExplain(String target) {
        String sql = "SELECT " + Database.COLUME_WORD_EXPLAIN + " FROM " +
                table + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
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
    //r
    public List<Word> getAllWord() {
        String sql = "SELECT " + Database.COLUME_WORD_TARGET + ", " + Database.COLUME_WORD_EXPLAIN + " FROM " + table;
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
    //u
    public void updateExplain(String target, String explain) {
        String sql = "UPDATE " + table + " SET " + Database.COLUME_WORD_EXPLAIN +
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
    //d
    public void deleteWord(String tager) {
        String sql = "DELETE FROM " + table + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tager);
            pstmt.executeUpdate();
        } catch (SQLException e) { }
    }

}
