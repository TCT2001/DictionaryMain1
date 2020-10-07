package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotesDB extends Table {
    public NotesDB() {
        table = "notes";
    }

    //false = noExist
    public Boolean isExist(String target) {
        String sql = "SELECT " + Database.COLUME_WORD_EXPLAIN + " FROM " +
                table + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, target);
            ResultSet rs = pstmt.executeQuery();
            if (rs.isClosed()) {
                return false;
            }
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
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

    public ArrayList<String> getWordFromNoteDB() {
        String sql = "SELECT " + Database.COLUME_WORD_TARGET + " FROM " + table;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String string = rs.getString(Database.COLUME_WORD_TARGET);
                arrayList.add(string);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }


}

