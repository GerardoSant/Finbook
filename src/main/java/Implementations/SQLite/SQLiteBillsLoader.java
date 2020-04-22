package Implementations.SQLite;

import Model.Bills.Bill;
import Controller.util.date.DateParser;
import View.loaders.BillsLoader;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteBillsLoader extends SQLiteLoader implements BillsLoader {

    @Override
    public List<Bill> loadBills(String RFC) {
        return getBills(RFC);
    }

    public List<Bill> getBills(String RFC){
        try{
            return getBillsFromDB(RFC);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private List<Bill> getBillsFromDB(String RFC) throws SQLException, ParseException {
        Connection conn = this.connect();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlQuery(RFC));
        return billListFromResultSet(resultSet);
    }

    private String sqlQuery(String RFC) {
        return "SELECT * FROM facturas where issuerRFC=" + queryRFC(RFC) + " OR " + "receiverRFC=" + queryRFC(RFC);
    }

    private String queryRFC(String RFC) {
        return "\"" + RFC + "\"";
    }

    private List<Bill> billListFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        List<Bill> billsList = new ArrayList<>();
        while(resultSet.next()){
            billsList.add(getBillFromResultSet(resultSet));
        }
        return billsList;
    }

    private Bill getBillFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        return new Bill(resultSet.getString("UUID"),new DateParser("yyyy-MM-dd HH:mm:ss").parseDate(resultSet.getString("Date")),
                resultSet.getInt("PC"),resultSet.getString("type"),resultSet.getString("use"),resultSet.getString("concept"),
                resultSet.getString("issuerName"),resultSet.getString("issuerRFC"),resultSet.getString("receiverName"), resultSet.getString("receiverRFC"),
                resultSet.getDouble("subtotal"),resultSet.getDouble("taxRate"),resultSet.getDouble("total"), resultSet.getString("currency"), resultSet.getString("xml"));
    }


}
