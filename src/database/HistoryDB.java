package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        } catch (SQLException e) {
        }
    }
}
