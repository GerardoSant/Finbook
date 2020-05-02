package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Bills.Bill;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.List;

public class LoadBillsCommand extends FrontCommand {

    private final static int PAGE_SIZE=30;

    @Override
    public String execute() throws ParseException {
        if (requestedPageNumber()*PAGE_SIZE<currentBillList().size()){
            return pageAsJson();
        } else{
            return notifyEnd();
        }
    }

    private String pageAsJson() {
        return new Gson().toJson(getBillListPage(currentBillList(), requestedPageNumber()));
    }

    private List<Bill> currentBillList() {
        return request.session().attribute("currentBillList");
    }

    private int requestedPageNumber() {
        return Integer.parseInt(request.queryParams("page"));
    }

    private String notifyEnd() {
        return new Gson().toJson("End");
    }


    private List<Bill> getBillListPage(List<Bill> billList, int pageNumber) {
        try{
            return billList.subList(pageNumber*PAGE_SIZE,pageNumber*PAGE_SIZE+PAGE_SIZE);
        } catch(IndexOutOfBoundsException exception){
            return billList.subList(pageNumber*PAGE_SIZE,billList.size());
        }
    }
}
