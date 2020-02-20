package Bills;

import util.DateParser;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteBillsLoader implements BillsLoader {


    public List<Bill> selectAll(String RFC){
        RFC="\"" + RFC + "\"";
        String sql = "SELECT * FROM facturas where issuerRFC=" + RFC + " OR " + "receiverRFC=" + RFC;
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
             return dbBillList(rs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private List<Bill> dbBillList(ResultSet resultSet) throws SQLException, ParseException {
        List<Bill> billsList = new ArrayList<>();
        while(resultSet.next()){
            billsList.add(new Bill(resultSet.getString("UUID"),parseDate(resultSet.getString("Date")),
                    resultSet.getInt("PC"),resultSet.getString("type"),resultSet.getString("use"),resultSet.getString("concept"),
                    resultSet.getString("issuerName"),resultSet.getString("issuerRFC"),resultSet.getString("receiverName"), resultSet.getString("receiverRFC"),
                    resultSet.getDouble("subtotal"),resultSet.getDouble("taxRate"),resultSet.getDouble("total"), resultSet.getString("currency"), resultSet.getString("xml")));
        }
        return billsList;
    }

    private Date parseDate(String date) throws ParseException {
        return new DateParser("yyyy-MM-dd HH:mm:ss").parseDate(date);
    }


    public Connection connect() {
        // SQLite connection string
        //String url = "jdbc:sqlite:C:\\Users\\pezpo\\Desktop\\SQLite\\Bills.db";
        String url = "jdbc:sqlite:C:\\Users\\pezpo\\Desktop\\SQLite\\facturasSQLite.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public List<Bill> loadBills(String RFC) {
        return selectAll(RFC);
    }
}
