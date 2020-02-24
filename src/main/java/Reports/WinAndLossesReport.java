package Reports;

import Bills.Bill;

import java.util.List;
import java.util.stream.Collectors;

public class WinAndLossesReport {

    private double salesAndIncomesBase;
    private double netSalesBase;
    private List<Bill> grossSales;
    private double grossSalesBase;
    private List<Bill> salesReturns;
    private double salesReturnsBase;
    private double purchasesAndExpenses;
    private double netPurchases;
    private List<Bill> grossPurchases;
    private double grossPurchasesBase;
    private List<Bill> purchasesReturns;
    private double purchasesReturnsBase;
    private List<Bill> externalServices;
    private double externalServicesBase;
    private List<Bill> salariesAndWages;
    private double salariesAndWagesBase;
    private double personnelExpenses;
    private String RFC;


    public WinAndLossesReport(List<Bill> billList, String RFC) {
        this.RFC=RFC;
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
        this.netSalesBase = this.grossPurchasesBase - this.purchasesReturnsBase;
        this.purchasesAndExpenses = netSalesBase + externalServicesBase + personnelExpenses;

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

    private double calculateBase(List<Bill> billList){
        return billList.stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    };
}
