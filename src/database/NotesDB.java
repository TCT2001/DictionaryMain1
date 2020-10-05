package database;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotesDB extends Table{
    public NotesDB(){
        table ="notes";
    }
    //false = noExist
    public Boolean isExist(String target){
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
//        String sql = "DELETE FROM dictionary WHERE instr(word_target, 'city-planning') > 0";
        String sql = "DELETE FROM " + table + " WHERE " + Database.COLUME_WORD_TARGET + " = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tager);
            pstmt.executeUpdate();
        } catch (SQLException e) { }
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

    public static void main(String[] args) {
        NotesDB notesDB = new NotesDB();
        notesDB.table ="dictionary";
        notesDB.updateExplain("he","/hi:/\n" +
                "*  đại từ\n" +
                "- nó, anh ấy, ông ấy... (chỉ người và động vật giống đực)\n" +
                "*  danh từ\n" +
                "- đàn ông; con đực\n" +
                "- (định ngữ) đực (động vật)");
        notesDB.updateExplain("fuck","/fʌk/\n"+
                "*  động từ\n"+
                "giao hợp, làm tình");
//        notesDB.updateExplain("he","dcm");
        System.out.println(notesDB.getExplain("he"));
    }

}

