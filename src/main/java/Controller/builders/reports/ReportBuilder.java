package Controller.builders.reports;

import Model.Bills.Bill;
import Controller.util.date.PeriodFinder;
import Model.Reports.Report;
import Controller.util.bill.BillFilter;

import java.util.Date;
import java.util.List;

public abstract class ReportBuilder {
    protected List<Bill> billList;
    protected String RFC;
    protected Date periodStart;
    protected Date periodEnd;

    public ReportBuilder(List<Bill> billList, String RFC, Date periodStart, Date periodEnd) {
        this.billList = billList;
        this.RFC = RFC;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public ReportBuilder(List<Bill> billList, String RFC) {
        this.billList = billList;
        this.RFC = RFC;
        this.periodStart= new PeriodFinder(billList).findPeriodStart();
        this.periodEnd= new PeriodFinder(billList).findPeriodEnd();
    }

    public abstract Report buildReport();

    protected List<Bill> generateBillsFromPeriod(List<Bill> billList){
        return BillFilter.filterByPeriod(billList, periodStart, periodEnd);
    }
}
