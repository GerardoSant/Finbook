package Implementations.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SQLiteLoader {

    protected Connection connect() {
        return SQLConnection.getConnection();
    }
}
