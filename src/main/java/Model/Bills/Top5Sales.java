package Model.Bills;

import Controller.util.bill.BillFilter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Top5Sales {


    private Bill[] top5;

    public Top5Sales(List<Bill> billList, String RFC) {
        this.top5 = generateTop5(billList, RFC);
    }

    public Bill[] getTop5() {
        return top5;
    }

    private Bill[] generateTop5(List<Bill> billList, String RFC) {
        return calculateTop5(BillFilter.filterBySales(billList, RFC), initialTop5Array(BillFilter.filterBySales(billList, RFC)));
}

    private Bill[] initialTop5Array(List<Bill> billList) {
        Bill[] initialArray = new Bill[5];
        for (int listPosition=0; listPosition<5; listPosition++) initialArray[listPosition]=billList.get(listPosition);
        return initialArray;
    }

    private Bill[] calculateTop5(List<Bill> billList, Bill[] top5sales) {
        for (Bill bill : billList) {
            if (top5sales[4].getTotal()<bill.getTotal()){
                top5sales[4]=bill;
                top5sales= Arrays.stream(top5sales).sorted(Comparator.comparing(Bill::getTotal).reversed()).toArray(Bill[]::new);
            }
        }
        return top5sales;
    }


}
