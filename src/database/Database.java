package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
    public static final String COLUME_WORD_TARGET = "word_target";
    public static final String COLUME_WORD_EXPLAIN = "word_explain";
    private static Connection connection = null;

    /**
     * create table with first run
     *
     * @param connection
     */
    private static void createTable(Connection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS dictionary (\n"
                + "	word_target text PRIMARY KEY,\n"
                + "	word_explain text NOT NULL);";
        String sql2 = "CREATE TABLE IF NOT EXISTS notes (\n"
                + "	word_target text PRIMARY KEY);";
        String sql3 = "CREATE TABLE IF NOT EXISTS histories (\n"
                + "	word_target text PRIMARY KEY);";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.execute(sql3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * colection to database
     *
     * @return
     */
    public static Connection createNewDatabase() {
        String url = "jdbc:sqlite:src/database/dictionary.db";
        try {
            Connection conn = DriverManager.getConnection(url);
            createTable(conn);
            return conn;
        } catch (SQLException e) {
            return null;
        }
    }


    public static Connection getDatabase() {
        if (connection == null) {
            connection = createNewDatabase();
        }
        connection = createNewDatabase();
        return connection;
    }

//    public static void main(String[] args) {
//        String sqlCommand = "DROP TABLE IF EXISTS 'notes'";
//        String sqlCommand2 = "DROP TABLE IF EXISTS 'histories'";
//
//        Statement stmt = null;
//        try {
//            connection.setAutoCommit(false);
//            stmt = connection.createStatement();
//            stmt.executeUpdate(sqlCommand);
//            stmt.executeUpdate(sqlCommand2);
//            connection.commit();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
}
