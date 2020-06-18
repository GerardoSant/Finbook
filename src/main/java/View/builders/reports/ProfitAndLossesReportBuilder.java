package View.builders.reports;

import Model.Bills.Bill;
import Model.Reports.ProfitAndLossesReport;
import Controller.util.bill.BillCalculator;
import Controller.util.bill.BillFilter;
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
        return generateProfitAndLossesReport(generateGrossSales(),generateSalesReturns(),generatePurchases("G01"),generatePurchases("G02"),generatePurchases("G03"),
                generateSalaries());
    }

    private ProfitAndLossesReport generateProfitAndLossesReport(List<Bill> grossSales,List<Bill> salesReturns, List<Bill> grossPurchases, List<Bill> purchasesReturns,
    List<Bill> externalServices, List<Bill> salariesAndWages){
        return new ProfitAndLossesReport(periodStart,periodEnd,RFC,grossSales, calculateBase(grossSales),
                salesReturns, calculateBase(salesReturns), grossPurchases, calculateBase(grossPurchases),
                purchasesReturns, calculateBase(purchasesReturns),externalServices, calculateBase(externalServices),
                salariesAndWages, calculateBase(salariesAndWages));
    }

    private double calculateBase(List<Bill> bills){
        return BillCalculator.calculateBase(bills);
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

    public ProfitAndLossesReport buildReducedReport(){
        billList= generateBillsFromPeriod(billList);
        return generateReducedProfitAndLossesReport(generateGrossSales(),generateSalesReturns(),generatePurchases("G01"),generatePurchases("G02"),generatePurchases("G03"),
                generateSalaries());
    }

    private ProfitAndLossesReport generateReducedProfitAndLossesReport(List<Bill> grossSales,List<Bill> salesReturns, List<Bill> grossPurchases, List<Bill> purchasesReturns,
                                                                       List<Bill> externalServices, List<Bill> salariesAndWages) {
        return new ProfitAndLossesReport(periodStart,periodEnd,RFC,reduceList(grossSales), calculateBase(grossSales),
                reduceList(salesReturns), calculateBase(salesReturns), reduceList(grossPurchases), calculateBase(grossPurchases),
                reduceList(purchasesReturns), calculateBase(purchasesReturns),reduceList(externalServices), calculateBase(externalServices),
                reduceList(salariesAndWages), calculateBase(salariesAndWages));
    }

    private List<Bill> reduceList(List<Bill> list) {
        try{
            return list.subList(0,15);
        } catch(Exception e) {
            return list;
        }
    }
}
