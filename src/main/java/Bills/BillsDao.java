package Bills;

import com.google.common.collect.ImmutableList;

import java.util.Date;
import java.util.List;

public class BillsDao {


    private final List<Bill> bills;
    private String userRFC;

    public BillsDao(String userRFC) {
        this.userRFC= userRFC;
        bills= new SQLiteBillsLoader().loadBills(userRFC);
    }


    public Iterable<Bill> getAllBills(){ return bills; }

    public Bill getBillByUUID(String s) {
        return bills.stream().filter(bill-> bill.getUUID().equals(s)).findFirst().orElse(null);
    }
}
