package Implementations.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {

    //public static final String databasePath = "jdbc:sqlite:src\\main\\resources\\db\\facturasSQLite.db";
    public static final String databasePath = "jdbc:sqlite:C:\\Users\\pezpo\\IdeaProjects\\FinSingle\\src\\main\\resources\\db\\backup\\facturasSQLite.db";

    private static Connection connection=null;

    public SQLiteConnection() {
        connectToDatabase();
    }

    private synchronized void connectToDatabase() {
        try {
            if(connection==null) connection= DriverManager.getConnection(databasePath);
        } catch (SQLException e) {
            System.out.println("Error creating connection:" + e.getMessage());
        }
    }

    public static Connection getConnection(){
        if (thereIsNotConnection()){
            new SQLiteConnection();
        }
        return connection;
    }

    private static boolean thereIsNotConnection() {
        return connection==null;
    }
}
