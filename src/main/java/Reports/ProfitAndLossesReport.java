package Reports;

import Bills.Bill;
import Bills.BillsDao;
import util.PeriodFinder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ProfitAndLossesReport extends Report {

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

    public ProfitAndLossesReport(Date periodStart, Date periodEnd, String RFC, List<Bill> grossSales, double grossSalesBase, List<Bill> salesReturns, double salesReturnsBase, List<Bill> grossPurchases, double grossPurchasesBase, List<Bill> purchasesReturns, double purchasesReturnsBase, List<Bill> externalServices, double externalServicesBase, List<Bill> salariesAndWages, double salariesAndWagesBase) {
        super(periodStart, periodEnd, RFC);
        this.grossSales = grossSales;
        this.grossSalesBase = grossSalesBase;
        this.salesReturns = salesReturns;
        this.salesReturnsBase = salesReturnsBase;
        this.grossPurchases = grossPurchases;
        this.grossPurchasesBase = grossPurchasesBase;
        this.purchasesReturns = purchasesReturns;
        this.purchasesReturnsBase = purchasesReturnsBase;
        this.externalServices = externalServices;
        this.externalServicesBase = externalServicesBase;
        this.salariesAndWages = salariesAndWages;
        this.salariesAndWagesBase = salariesAndWagesBase;
        this.netSalesBase=grossSalesBase-salesReturnsBase;
        this.personnelExpenses= salariesAndWagesBase;
        this.netPurchasesBase= grossPurchasesBase-purchasesReturnsBase;
        this.purchasesAndExpensesBase= netPurchasesBase + externalServicesBase + personnelExpenses;
        this.salesAndIncomesBase= this.netSalesBase;
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
