package Controller.util.bill;

import Model.Bills.Bill;
import java.util.List;

public class BillCalculator {

    public static double calculateBase(List<Bill> billList){
        return billList.stream().map(bill -> bill.getSubtotal()).reduce(0.0, (subtotal, bill) -> subtotal + bill);
    };

}
