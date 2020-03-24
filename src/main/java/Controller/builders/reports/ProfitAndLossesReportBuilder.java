package Controller.builders.reports;

import Model.Bills.Bill;
import Model.Reports.ProfitAndLossesReport;
import Controller.util.BillCalculator;
import Controller.util.BillFilter;

import java.util.Date;
import java.util.List;


public class ProfitAndLossesReportBuilder extends ReportBuilder {

    public ProfitAndLossesReportBuilder(List<Bill> billList, String RFC, Date periodStart, Date periodEnd) {
        super(billList, RFC, periodStart, periodEnd);
    }

    public ProfitAndLossesReportBuilder(List<Bill> billList, String RFC) {
        super(billList, RFC);
    }

    @Override
    public ProfitAndLossesReport buildReport() {
        billList= generateBillsFromPeriod(billList);
        return new ProfitAndLossesReport(periodStart, periodEnd, RFC,
                generateGrossSales(), BillCalculator.calculateBase(generateGrossSales()),generateSalesReturns(), BillCalculator.calculateBase(generateSalesReturns()),
                generatePurchases("G01"), BillCalculator.calculateBase(generatePurchases("G01")),
                generatePurchases("G02"), BillCalculator.calculateBase(generatePurchases("G02")),
                generatePurchases("G03"), BillCalculator.calculateBase(generatePurchases("G03")),
                generateSalaries(),BillCalculator.calculateBase(generateSalaries())
                );

    }

    private List<Bill> generateSalaries() {
        return BillFilter.filterBySalaries(billList, RFC);
    }

    private List<Bill> generatePurchases(String purhcaseUseCode) {
        return BillFilter.filterByPurchases(billList,purhcaseUseCode, RFC);
    }

    private List<Bill> generateSalesReturns() {
        return BillFilter.filterByReturns(billList, RFC);
    }

    private List<Bill> generateGrossSales(){
        return BillFilter.filterByGrossSales(billList, RFC);
    }
}
