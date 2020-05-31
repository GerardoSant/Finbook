package Model.Reports;

import Model.Bills.Bill;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Report {

    protected Date periodStart;
    protected Date periodEnd;
    protected String RFC;

    public Report(Date periodStart, Date periodEnd, String RFC) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.RFC = RFC;
    }

    public String getRFC() {
        return RFC;
    }

    public Date getPeriodStart() {
        return periodStart;
    }

    public Date getPeriodEnd() {
        return periodEnd;
    }

    public double calculateBase(List<Bill> billList){
        return billList.stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    };


}
