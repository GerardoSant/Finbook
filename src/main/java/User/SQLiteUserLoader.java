package User;

import SQLite.SQLiteLoader;

import java.sql.*;

public class SQLiteUserLoader extends SQLiteLoader implements UserLoader{


    @Override
    public String loadUser(String RFC) {
        return selectUser(RFC);
    }

    private String selectUser(String RFC) {
        RFC="\"" + RFC + "\"";
        String sql = "SELECT * FROM empresas where receiverRFC=" + RFC;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
             return rs.getString("receiverName");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
