package Implementations.SQLite;

import java.sql.Connection;

public abstract class SQLiteConnector {

    protected Connection connect() {
        return SQLiteConnection.getConnection();
    }
}
