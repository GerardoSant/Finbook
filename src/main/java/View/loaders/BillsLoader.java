package View.loaders;

import Model.Bills.Bill;

import java.util.List;

public interface BillsLoader {
    List<Bill> loadBills(String RFC);
}
