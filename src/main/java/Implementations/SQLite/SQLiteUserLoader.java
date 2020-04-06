package Implementations.SQLite;

import Model.User.User;
import View.loaders.UserLoader;

import java.sql.*;

public class SQLiteUserLoader extends SQLiteLoader implements UserLoader {


    @Override
    public User loadUser(String RFC) {
        return selectUser(RFC);
    }

    private User selectUser(String RFC) {
        RFC="\"" + RFC + "\"";
        String sql = "SELECT * FROM empresas where receiverRFC=" + RFC;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
             return new User(rs.getString("receiverName"),RFC);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
