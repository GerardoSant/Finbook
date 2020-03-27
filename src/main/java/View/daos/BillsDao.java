package View.daos;

import Implementations.SQLite.SQLiteBillsLoader;
import Model.Bills.Bill;

import java.util.List;

public class BillsDao {


    private final List<Bill> bills;
    private String userRFC;

    public BillsDao(String userRFC) {
        this.userRFC= userRFC;
        this.bills= new SQLiteBillsLoader().loadBills(userRFC);
    }


    public List<Bill> getAllBills(){ return bills; }

    public Bill getBillByUUID(String s) {
        return bills.stream().filter(bill-> bill.getUUID().equals(s)).findFirst().orElse(null);
    }
}
