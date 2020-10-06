package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HistoryDB extends Table {
    public HistoryDB() {
        table = "histories";
    }
    public void deleteWord(String tager) {
        String sql = "DELETE FROM " + table + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tager);
            pstmt.executeUpdate();
        } catch (SQLException e) { }
    }

    public ArrayList<String> getWordFromHistoryDB() {
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

//    public static void main(String[] args) {
//        HistoryDB historyDB = new HistoryDB();
//        ArrayList<String> arrayList = historyDB.getWordFromHistoryDB();
//        for(String string : arrayList){
//            System.out.println(string);
//        }
//    }
}
