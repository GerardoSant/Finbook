package Implementations.SQLite;

import Model.User.User;
import View.loaders.UserLoader;

import java.sql.*;

public class SQLiteUserLoader extends SQLiteLoader implements UserLoader {

    @Override
    public User loadUser(String RFC) {
        return getUser(RFC);
    }

    private User getUser(String RFC) {
        try {
            return getUserFromDB(RFC);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private User getUserFromDB(String RFC) throws SQLException {
        Connection connection = this.connect();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery(RFC));
        return new User(rs.getString("receiverName"), RFC);
    }

    private String sqlQuery(String RFC) {
        return "SELECT * FROM empresas where receiverRFC=" + queryRFC(RFC);
    }

    private String queryRFC(String RFC) {
        return "\"" + RFC + "\"";
    }
}
