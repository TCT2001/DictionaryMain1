package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DictionanryDB extends Table {
    public DictionanryDB() {
        table = "dictionary";
    }

    public List<String> getAllWordHint(String text) {
        String sql = "SELECT *\n" +
                "  FROM dictionary\n" +
                " WHERE instr(word_target, '" + text + "') = 1 LIMIT 0,13";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            List<String> res = new ArrayList<>();
            while (rs.next()) {
                if (rs.getString(Database.COLUME_WORD_TARGET).compareTo("") != 0) {
                    res.add(rs.getString(Database.COLUME_WORD_TARGET));
                }

            }
            return res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
