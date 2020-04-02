package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.BillFilter;
import Model.Bills.Bill;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.List;

public class FilterBillsCommand extends FrontCommand {
    @Override
    public String process() throws ParseException {
        List<Bill> billList = request.session().attribute("billList");
        String receiverName = request.queryParams("receiver");
        billList= BillFilter.filterByReceiver(billList, receiverName);
        request.session().attribute("currentBillList",billList);
        try {
            return new Gson().toJson(billList.subList(0,30));
        } catch(IndexOutOfBoundsException e){
            return new Gson().toJson(billList.subList(0,billList.size()));
        }

    }
}
