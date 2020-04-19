package Model.Bills;

import Controller.util.bill.BillFilter;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BillTimeline {

    private List<Bill> billList;

    public BillTimeline(List<Bill> billList) {
        this.billList = sortListAscendingOrder(billList);
    }

    public BillTimeline(List<Bill> billList, boolean ascendent) {
        if (ascendent){
            this.billList = sortListAscendingOrder(billList);
        } else{
            this.billList = sortListDescendingOrder(billList);
        }
    }

    public BillTimeline(List<Bill> billList, boolean ascendent, Date periodStart, Date periodEnd){
        this(billList, ascendent);
        this.billList = BillFilter.filterByPeriod(this.billList, periodStart, periodEnd);
    }


    private List<Bill> sortListAscendingOrder(List<Bill> billList){
        return billList.stream()
                .sorted(Comparator.comparing(Bill::getDate))
                .collect(Collectors.toList());
    }

    private List<Bill> sortListDescendingOrder(List<Bill> billList) {
        return billList.stream()
                .sorted(Comparator.comparing(Bill::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }
}
