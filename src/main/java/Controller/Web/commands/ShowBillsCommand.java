package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.BillFilter;
import Model.Bills.Bill;
import View.daos.BillsDao;
import Controller.Web.login.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import Controller.util.Path;
import Controller.util.ViewUtil;

import java.util.HashMap;
import java.util.List;

import static Controller.util.RequestUtil.getSessionCurrentUser;

public class ShowBillsCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        List<Bill> billList = new BillsDao(getSessionCurrentUser(request)).getAllBills();
        HashMap<String, Object> model = new HashMap<>();
        billList=  handleQuery(billList, model);
        List<Bill> initialBillList = billList.subList(0,30);
        request.session().attribute("billList",billList);
        request.session().attribute("currentBillList",billList);
        model.put("bills",initialBillList);
        model.put("math", new MathTool());
        model.put("dateTool", new DateTool());
        return ViewUtil.render(request, model, Path.Template.BILLS2);
    }

    private List<Bill> handleQuery(List<Bill> billList, HashMap<String, Object> model) {
        if(request.queryParams("class")!=null){
            if (request.queryParams("class").equals("received")) {
                model.put("received", true);
                return BillFilter.filterByReceived(billList, getSessionCurrentUser(request));
            }
        }
        return BillFilter.filterByIssued(billList,getSessionCurrentUser(request));
    }
}
