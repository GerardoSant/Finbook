package Bills;

import Reports.BillFilter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillTimelineBuilder {
    public BillTimeline build(String RFC, List<Bill> billList, boolean ascendent, double minTotal, double maxTotal, boolean incomes, boolean expenses, boolean investments, boolean salaries) {
        billList = buildBillList(RFC, billList, minTotal, maxTotal, incomes, expenses, investments, salaries);
        return new BillTimeline(billList,ascendent);
    }

    public BillTimeline build(String RFC, List<Bill> billList, Date periodStart, Date periodEnd,
                              boolean ascendent, double minTotal, double maxTotal, boolean incomes, boolean expenses, boolean investments, boolean salaries) {
        billList = buildBillList(RFC, billList, minTotal, maxTotal, incomes, expenses, investments, salaries);
        return new BillTimeline(billList,ascendent,periodStart,periodEnd);
    }

    public BillTimeline build(String RFC, List<Bill> billList, boolean ascendent, boolean incomes, boolean expenses, boolean investments, boolean salaries){
        billList = filterByBillType(RFC, billList, incomes, expenses, investments, salaries);
        return new BillTimeline(billList,ascendent);
    }

    public BillTimeline build(String RFC, List<Bill> billList,Date periodStart, Date periodEnd, boolean ascendent, boolean incomes, boolean expenses, boolean investments, boolean salaries){
        billList = filterByBillType(RFC, billList, incomes, expenses, investments, salaries);
        return new BillTimeline(billList,ascendent, periodStart, periodEnd);
    }


    private List<Bill> buildBillList(String RFC, List<Bill> billList, double minTotal, double maxTotal, boolean incomes, boolean expenses, boolean investments, boolean salaries) {
        billList = BillFilter.filterByTotalRange(billList, minTotal, maxTotal);
        billList = filterByBillType(RFC, billList, incomes, expenses, investments, salaries);
        return billList;
    }

    private List<Bill> filterByBillType(String RFC, List<Bill> billList, boolean incomes, boolean expenses, boolean investments, boolean salaries) {
        List<Bill> typesBillList = new ArrayList<>();
        if (incomes) typesBillList.addAll(BillFilter.filterBySales(billList, RFC));
        if (expenses) typesBillList.addAll(BillFilter.filterByExpenses(billList, RFC));
        if (investments) typesBillList.addAll(BillFilter.filterByInvestments(billList,RFC));
        if (salaries) typesBillList.addAll(BillFilter.filterBySalaries(billList, RFC));
        return typesBillList;
    }
}