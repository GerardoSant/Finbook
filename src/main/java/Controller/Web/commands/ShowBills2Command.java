package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.BillFilter;
import Controller.util.Path;
import Controller.util.ViewUtil;
import Model.Bills.Bill;
import View.daos.BillsDao;
import org.apache.velocity.tools.generic.MathTool;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import static Controller.util.RequestUtil.getSessionCurrentUser;

public class ShowBills2Command extends FrontCommand {

    @Override
    public String process()  {
        List<Bill> billList = new BillsDao("E-5756930").getAllBills();
        HashMap<String, Object> model = new HashMap<>();
        billList=  handleQuery(billList, model);
        List<Bill> initialBillList = billList.subList(0,30);
        request.session().attribute("billList",billList);
        request.session().attribute("currentBillList",billList);
        model.put("bills",initialBillList);
        model.put("math", new MathTool());
        return ViewUtil.render(request, model, Path.Template.BILLS2);
    }

    private List<Bill> handleQuery(List<Bill> billList, HashMap<String, Object> model) {
        if(request.queryParams("class")!=null){
            if (request.queryParams("class").equals("received")){
                model.put("received", true);
                return BillFilter.filterByReceived(billList,"E-5756930");
            } else{
                return BillFilter.filterByIssued(billList,"E-5756930");
            }
        } else{
            return BillFilter.filterByIssued(billList,"E-5756930");
        }
    }


}
