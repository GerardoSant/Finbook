package Bills;

import java.util.List;

public interface BillsLoader {
    List<Bill> loadBills(String RFC);
}
