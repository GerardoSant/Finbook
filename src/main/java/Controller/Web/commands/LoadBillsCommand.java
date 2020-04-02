package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Bills.Bill;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.List;

public class LoadBillsCommand extends FrontCommand {

    private final static int PAGE_SIZE=30;
    @Override
    public String process() throws ParseException {
        List<Bill> billList= request.session().attribute("currentBillList");
        int pageNumber = Integer.parseInt(request.queryParams("page"));
        System.out.println(pageNumber);
        if (pageNumber*PAGE_SIZE<billList.size()){
            return new Gson().toJson(getPageBillList(billList, pageNumber));
        } else{
            return new Gson().toJson("End");
        }


    }

    private List<Bill> getPageBillList(List<Bill> billList, int pageNumber) {
        try{
            return billList.subList(pageNumber*PAGE_SIZE,pageNumber*PAGE_SIZE+PAGE_SIZE);
        } catch(IndexOutOfBoundsException exception){
            return billList.subList(pageNumber*PAGE_SIZE,billList.size());
        }
    }
}
