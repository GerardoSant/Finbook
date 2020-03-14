package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SQLiteLoader {


    protected Connection connect() {
        String url = "jdbc:sqlite:C:\\Users\\pezpo\\Desktop\\SQLite\\facturasSQLite.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
