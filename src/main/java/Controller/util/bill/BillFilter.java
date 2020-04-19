package Controller.util.bill;

import Model.Bills.Bill;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BillFilter {

    private static final String INVESTMENTS_CODE_REGEX ="I0[1-8]+";
    private static final String PAYROLL="nomina";
    private static final String OUTCOME="egreso";
    private static final String PURCHASES_CODE="G01";
    private static final String EXTERNAL_SERVICES_CODE="G03";

    public static List<Bill> filter(List<Bill> billList, Predicate<Bill> filterRule){
        return billList.stream().filter(filterRule).collect(Collectors.toList());
    }
    public static List<Bill> filterByInvestments(List<Bill> billList, String useCode, String RFC){
        return filter(billList,bill -> bill.getUse().equals(useCode) && bill.getReceiverRFC().equals(RFC));
    }

    public static List<Bill> filterByInvestments(List<Bill> billList, String RFC){
        return filter(billList,bill -> (bill.getUse().matches(INVESTMENTS_CODE_REGEX)) && bill.getReceiverRFC().equals(RFC));
    }

    public static List<Bill> filterByPeriod(List<Bill> billList, Date periodStart, Date periodEnd){
        return filter(billList, bill -> bill.getDate().compareTo(periodStart)>=0 && bill.getDate().compareTo(periodEnd)<=0);
    }

    public static List<Bill> filterByGrossSales(List<Bill> billList, String RFC){
        return filter(billList,bill-> bill.getIssuerRFC().equals(RFC) && !bill.getType().equals(PAYROLL));
    }

    public static List<Bill> filterBySales(List<Bill> billList, String RFC){
        return filter(billList,bill-> bill.getIssuerRFC().equals(RFC) && !bill.getType().equals(PAYROLL) && !bill.getType().equals(OUTCOME));
    }

    public static List<Bill> filterByReturns(List<Bill> billList, String RFC){
        return filter(billList,bill-> bill.getIssuerRFC().equals(RFC) && bill.getType().equals(OUTCOME));
    }

    public static List<Bill> filterByPurchases(List<Bill> billList, String purchaseUseCode,String RFC) {
        return filter(billList,bill -> bill.getUse().equals(purchaseUseCode) && bill.getReceiverRFC().equals(RFC));
    }

    public static List<Bill> filterByPurchasesAndExternalServices(List<Bill> billList, String RFC) {
        return filter(billList,bill -> (bill.getUse().equals(PURCHASES_CODE) || bill.getUse().equals(EXTERNAL_SERVICES_CODE))  && bill.getReceiverRFC().equals(RFC));
    }

    public static List<Bill> filterByPurchases(List<Bill> billList, String RFC) {
        return filter(billList,bill -> (bill.getUse().equals(PURCHASES_CODE)  && bill.getReceiverRFC().equals(RFC)));
    }

    public static List<Bill> filterByExternalServices(List<Bill> billList, String RFC) {
        return filter(billList,bill -> (bill.getUse().equals(EXTERNAL_SERVICES_CODE))  && bill.getReceiverRFC().equals(RFC));
    }

    public static List<Bill> filterBySalaries(List<Bill> billList, String RFC){
        return filter(billList,bill-> bill.getIssuerRFC().equals(RFC) && bill.getType().equals(PAYROLL));
    }

    public static List<Bill> filterByTotalRange(List<Bill> billList, double minTotal, double maxTotal){
        return filter(billList,bill-> bill.getTotal()>=minTotal && bill.getTotal()<=maxTotal);
    }
    public static List<Bill> filterByMinTotal(List<Bill> billList, double minTotal){
        return filter(billList,bill-> bill.getTotal()>=minTotal);
    }

    public static List<Bill> filterByMaxTotal(List<Bill> billList, double maxTotal){
        return filter(billList,bill-> bill.getTotal()<=maxTotal);
    }

    public static List<Bill> filterByIssued(List<Bill> billList, String RFC){
        return filter(billList,bill-> bill.getIssuerRFC().equals(RFC));
    }

    public static List<Bill> filterByReceived(List<Bill> billList, String RFC){
        return filter(billList,bill-> bill.getReceiverRFC().equals(RFC));
    }

    public static List<Bill> filterByPeriodStart(List<Bill> billList, Date periodStart) {
        return filter(billList,bill-> bill.getDate().compareTo(periodStart)>=0);
    }

    public static List<Bill> filterByPeriodEnd(List<Bill> billList, Date periodEnd) {
        return filter(billList,bill-> bill.getDate().compareTo(periodEnd)<=0);
    }

    public static List<Bill> filterByReceiver(List<Bill> billList, String receiverName) {
        return filter(billList,bill-> bill.getReceiverName().contains(receiverName));
    }

    public static List<Bill> filterByIssuer(List<Bill> billList, String issuerName) {
        return filter(billList,bill-> bill.getIssuerName().contains(issuerName));
    }

    public static List<Bill> filterByPC(List<Bill> billList, String PC) {
        return filter(billList,bill -> string(bill.getPC()).contains(PC));
    }

    private static String string(int integer) {
        return String.valueOf(integer);
    }



}
