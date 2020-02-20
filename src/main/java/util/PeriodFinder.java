package util;

import Bills.Bill;
import Bills.BillsLoader;
import Bills.SQLiteBillsLoader;

import java.util.Date;
import java.util.List;

public class PeriodFinder {

    private List<Bill> billList;

    public PeriodFinder(List<Bill> billList) {
        this.billList=billList;
    }

    public Date findPeriodStart(){
        return billList.stream().map(bill -> bill.getDate()).min(Date::compareTo).get();
    }

    public Date findPeriodEnd(){
        return billList.stream().map(bill -> bill.getDate()).max(Date::compareTo).get();
    }
}
