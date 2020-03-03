package Reports;

import Bills.Bill;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Report {

    private Date periodStart;
    private Date periodEnd;
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

    protected double calculateBase(List<Bill> billList){
        return billList.stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    };

    protected List<Bill> billsFromPeriod(List<Bill> billList){
        return billList.stream().filter(bill -> bill.getDate().compareTo(periodStart)>=0 && bill.getDate().compareTo(periodEnd)<=0).collect(Collectors.toList());
    }




}