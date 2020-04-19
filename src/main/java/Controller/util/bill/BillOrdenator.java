package Controller.util.bill;

import Model.Bills.Bill;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public  class BillOrdenator {

    public static List<Bill> order(List<Bill> billList, boolean descendent, Comparator<Bill> comparator){
        return descendent ? orderDescending(billList, comparator) : orderAscending(billList, comparator);
    }

    private static List<Bill> orderAscending(List<Bill> billList, Comparator<Bill> comparator) {
        return billList.stream().sorted(comparator).collect(Collectors.toList());
    }

    private static List<Bill> orderDescending(List<Bill> billList, Comparator<Bill> comparator) {
        return billList.stream().sorted(comparator.reversed()).collect(Collectors.toList());
    }
    public static List<Bill> orderByDate(List<Bill> billList, boolean descendent){
        return order(billList,descendent,Comparator.comparing(Bill::getDate));
    }

    public static List<Bill> orderByPC(List<Bill> billList, boolean descendent){
        return order(billList,descendent,Comparator.comparing(Bill::getPC));
    }

    public static List<Bill> orderByType(List<Bill> billList, boolean descendent) {
        return order(billList,descendent,Comparator.comparing(Bill::getType));
    }

    public static List<Bill> orderByIssuer(List<Bill> billList, boolean descendent) {
        return order(billList,descendent,Comparator.comparing(Bill::getIssuerName));
    }

    public static List<Bill> orderByReceiver(List<Bill> billList, boolean descendent) {
        return order(billList,descendent,Comparator.comparing(Bill::getReceiverName));
    }

    public static List<Bill> orderByTotal(List<Bill> billList, boolean descendent) {
        return order(billList,descendent,Comparator.comparing(Bill::getTotal));
    }

    public static List<Bill> orderByCurrency(List<Bill> billList, boolean descendent) {
        return order(billList,descendent,Comparator.comparing(Bill::getCurrency));
    }
}
