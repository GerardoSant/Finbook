package Implementations.SQLite;

import Controller.util.date.DateParser;
import Model.Bills.Bill;
import View.writers.BillWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class SQLiteBillWriter extends SQLiteConnector implements BillWriter {

    public static void vamo() {
        for (int i =1001; i<2000; i++) {
           new SQLiteBillWriter().writeBill(new Bill("813122M"+i, new Date(), 35017, "income", "G01", "Adquisicón de mercancías", "antendu Networks",
                    "X-5808465", "xd", "xd", 19.0, 21.0, 15.0, "EUR", null));
        }
    }
    @Override
    public boolean writeBill(Bill bill) {
        return insert(bill);
    }

    public boolean insert(Bill bill){
        String sql = "INSERT INTO Bills(UUID, Date, PC, use, type, concept, issuerName, issuerRFC, receiverName, receiverRFC, subtotal, taxRate, total, currency, xml) " +
                     "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,bill.getUUID());
            pstmt.setString(2,new DateParser("yyyy-MM-dd HH:mm:ss").toString(bill.getDate()));
            pstmt.setInt(3,bill.getPC());
            pstmt.setString(4,bill.getType());
            pstmt.setString(5,bill.getUse());
            pstmt.setString(6,bill.getConcept());
            pstmt.setString(7,bill.getIssuerName());
            pstmt.setString(8,bill.getIssuerRFC());
            pstmt.setString(9,bill.getReceiverName());
            pstmt.setString(10,bill.getReceiverRFC());
            pstmt.setDouble(11,bill.getSubtotal());
            pstmt.setDouble(12,bill.getTaxRate());
            pstmt.setDouble(13,bill.getTotal());
            pstmt.setString(14,bill.getCurrency());
            pstmt.setString(15,bill.getXmlFile());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
