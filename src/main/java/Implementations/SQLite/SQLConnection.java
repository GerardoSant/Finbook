package Implementations.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    //public static final String databasePath = "jdbc:sqlite:src\\main\\resources\\db\\facturasSQLite.db";
    public static final String databasePath = "jdbc:sqlite:C:\\Users\\pezpo\\IdeaProjects\\FinSingle\\src\\main\\resources\\db\\facturasSQLite.db";

    private static Connection connection=null;

    public SQLConnection() {
        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            connection= DriverManager.getConnection(databasePath);
        } catch (SQLException e) {
            System.out.println("Error creating connection:" + e.getMessage());
        }
    }

    public static Connection getConnection(){
        if (thereIsNotConnection()){
            new SQLConnection();
        }
        return connection;
    }

    private static boolean thereIsNotConnection() {
        return connection==null;
    }
}
