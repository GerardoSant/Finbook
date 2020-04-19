package Model.Bills;

import Controller.util.bill.BillFilter;

import java.util.List;

public class BillsDistribution {

    private double salesAmount;
    private double purchasesAndServicesAmount;
    private double investmentsAmount;
    private double salariesAmount;

    public BillsDistribution(List<Bill> billList, String RFC) {
        this.salesAmount = calculateSalesAmount(billList,RFC);
        this.purchasesAndServicesAmount = calculatePurchasesAndServicesAmount(billList,RFC);
        this.investmentsAmount = calculateInvestmentsAmount(billList,RFC);
        this.salariesAmount = calculateSalariesAmount(billList,RFC);
    }

    private double calculateSalariesAmount(List<Bill> billList, String RFC) {
        return BillFilter.filterBySalaries(billList, RFC).stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    }

    private double calculateInvestmentsAmount(List<Bill> billList, String RFC) {
        return BillFilter.filterByInvestments(billList,RFC).stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    }

    private double calculatePurchasesAndServicesAmount(List<Bill> billList, String RFC) {
        return BillFilter.filterByPurchasesAndExternalServices(billList,RFC).stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    }

    private double calculateSalesAmount(List<Bill> billList, String RFC) {
        return BillFilter.filterBySales(billList,RFC).stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    }

    public double getSalesAmount() {
        return salesAmount;
    }

    public double getPurchasesAndServicesAmount() {
        return purchasesAndServicesAmount;
    }

    public double getInvestmentsAmount() {
        return investmentsAmount;
    }

    public double getSalariesAmount() {
        return salariesAmount;
    }
}
