package Reports;

import Bills.Bill;
import Bills.BillsDao;
import Bills.SQLiteBillsLoader;
import util.PeriodFinder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class WinAndLossesReport extends Report {

    public static void main(String[] args) {
        List<Bill> billList = new BillsDao("C-6480477").getAllBills();
        WinAndLossesReport report = new WinAndLossesReport(billList,"C-6480477", new PeriodFinder(billList).findPeriodStart(),
                new PeriodFinder(billList).findPeriodEnd());
        report.getExternalServices().forEach(bill-> System.out.println(bill));
        System.out.println(report.getExternalServicesBase());
        report.getGrossSales().forEach(bill -> System.out.println(bill));
    }

    private double salesAndIncomesBase;
    private double netSalesBase;
    private List<Bill> grossSales;
    private double grossSalesBase;
    private List<Bill> salesReturns;
    private double salesReturnsBase;
    private double purchasesAndExpensesBase;
    private double netPurchasesBase;
    private List<Bill> grossPurchases;
    private double grossPurchasesBase;
    private List<Bill> purchasesReturns;
    private double purchasesReturnsBase;
    private List<Bill> externalServices;
    private double externalServicesBase;
    private List<Bill> salariesAndWages;
    private double salariesAndWagesBase;
    private double personnelExpenses;


    public WinAndLossesReport(List<Bill> billList, String RFC, Date periodStart, Date periodEnd) {
        super(periodStart, periodEnd, RFC);
        billList= billsFromPeriod(billList);
        this.grossSales = filterBySales(billList);
        this.grossSalesBase=calculateBase(grossSales);
        this.salesReturns = filterByReturns(billList);
        this.salesReturnsBase= calculateBase(salesReturns);
        this.netSalesBase= grossSalesBase-salesReturnsBase;
        this.salesAndIncomesBase= this.netSalesBase;
        this.grossPurchases = filterByUseCode(billList,"G01");
        this.grossPurchasesBase= calculateBase(grossPurchases);
        this.purchasesReturns = filterByUseCode(billList, "G02");
        this.purchasesReturnsBase = calculateBase(purchasesReturns);
        this.externalServices = filterByUseCode(billList,"G03");
        this.externalServicesBase = calculateBase(externalServices);
        this.salariesAndWages = filterBySalaries(billList);
        this.salariesAndWagesBase = calculateBase(salariesAndWages);
        this.personnelExpenses= salariesAndWagesBase;
        this.netPurchasesBase = grossPurchasesBase-purchasesReturnsBase;
        this.purchasesAndExpensesBase = netPurchasesBase + externalServicesBase + personnelExpenses;

    }

    private List<Bill> filterBySales(List<Bill> billList){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(this.RFC) && !bill.getType().equals("nomina")).collect(Collectors.toList());
    }

    private List<Bill> filterByUseCode(List<Bill> billList, String useCode) {
        return billList.stream().filter(bill -> bill.getUse().equals(useCode) && bill.getReceiverRFC().equals(this.RFC)).collect(Collectors.toList());
    }


    private List<Bill> filterByReturns(List<Bill> billList){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(this.RFC) && bill.getType().equals("egreso")).collect(Collectors.toList());
    }

    private List<Bill> filterBySalaries(List<Bill> billList){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(this.RFC) && bill.getType().equals("nomina")).collect(Collectors.toList());
    }

    public double getSalesAndIncomesBase() {
        return salesAndIncomesBase;
    }

    public double getNetSalesBase() {
        return netSalesBase;
    }

    public List<Bill> getGrossSales() {
        return grossSales;
    }

    public double getGrossSalesBase() {
        return grossSalesBase;
    }

    public List<Bill> getSalesReturns() {
        return salesReturns;
    }

    public double getSalesReturnsBase() {
        return salesReturnsBase;
    }

    public double getPurchasesAndExpensesBase() {
        return purchasesAndExpensesBase;
    }

    public double getNetPurchasesBase() {
        return netPurchasesBase;
    }

    public List<Bill> getGrossPurchases() {
        return grossPurchases;
    }

    public double getGrossPurchasesBase() {
        return grossPurchasesBase;
    }

    public List<Bill> getPurchasesReturns() {
        return purchasesReturns;
    }

    public double getPurchasesReturnsBase() {
        return purchasesReturnsBase;
    }

    public List<Bill> getExternalServices() {
        return externalServices;
    }

    public double getExternalServicesBase() {
        return externalServicesBase;
    }

    public List<Bill> getSalariesAndWages() {
        return salariesAndWages;
    }

    public double getSalariesAndWagesBase() {
        return salariesAndWagesBase;
    }

    public double getPersonnelExpenses() {
        return personnelExpenses;
    }
}
