package Controller.util;

import Model.Bills.Bill;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BillFilter {

    public static List<Bill> filterByInvestments(List<Bill> billList, String useCode, String RFC){
        return billList.stream().filter(bill -> bill.getUse().equals(useCode) && bill.getReceiverRFC().equals(RFC)).collect(Collectors.toList());
    }

    public static List<Bill> filterByInvestments(List<Bill> billList, String RFC){
        return billList.stream().filter(bill -> (bill.getUse().matches("I0[1-8]+")) && bill.getReceiverRFC().equals(RFC)).collect(Collectors.toList());
    }

    public static List<Bill> filterByPeriod(List<Bill> billList, Date periodStart, Date periodEnd){
        return billList.stream().filter(bill -> bill.getDate().compareTo(periodStart)>=0 && bill.getDate().compareTo(periodEnd)<=0).collect(Collectors.toList());
    }

    public static List<Bill> filterByGrossSales(List<Bill> billList, String RFC){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(RFC) && !bill.getType().equals("nomina")).collect(Collectors.toList());
    }

    public static List<Bill> filterBySales(List<Bill> billList, String RFC){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(RFC) && !bill.getType().equals("nomina") && !bill.getType().equals("egreso")).collect(Collectors.toList());
    }

    public static List<Bill> filterByReturns(List<Bill> billList, String RFC){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(RFC) && bill.getType().equals("egreso")).collect(Collectors.toList());
    }

    public static List<Bill> filterByPurchases(List<Bill> billList, String purchaseUseCode,String RFC) {
        return billList.stream().filter(bill -> bill.getUse().equals(purchaseUseCode) && bill.getReceiverRFC().equals(RFC)).collect(Collectors.toList());
    }

    public static List<Bill> filterByPurchasesAndExternalServices(List<Bill> billList, String RFC) {
        return billList.stream().filter(bill -> (bill.getUse().equals("G01") || bill.getUse().equals("G03"))  && bill.getReceiverRFC().equals(RFC)).collect(Collectors.toList());
    }

    public static List<Bill> filterBySalaries(List<Bill> billList, String RFC){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(RFC) && bill.getType().equals("nomina")).collect(Collectors.toList());
    }

    public static List<Bill> filterByTotalRange(List<Bill> billList, double minTotal, double maxTotal){
        return billList.stream().filter(bill-> bill.getTotal()>=minTotal && bill.getTotal()<=maxTotal).collect(Collectors.toList());
    }

    public static List<Bill> filterByIssued(List<Bill> billList, String RFC){
        return billList.stream().filter(bill-> bill.getIssuerRFC().equals(RFC)).collect(Collectors.toList());
    }

    public static List<Bill> filterByReceived(List<Bill> billList, String RFC){
        return billList.stream().filter(bill-> bill.getReceiverRFC().equals(RFC)).collect(Collectors.toList());
    }

    public static List<Bill> filterByReceiver(List<Bill> billList, String receiverName) {
        return billList.stream().filter(bill-> bill.getReceiverName().contains(receiverName)).collect(Collectors.toList());
    }
}