package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.util.bill.BillFilter;
import Model.Bills.Bill;
import View.daos.BillsDao;
import Controller.Web.controllers.LoginController;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import org.apache.velocity.tools.generic.NumberTool;

import java.util.HashMap;
import java.util.List;

import static Controller.Web.webutils.RequestUtil.getSessionUser;

public class ShowBillsCommand extends FrontCommand {

    List<Bill> billList;
    @Override
    public String execute() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        billList=  handleQuery(getRequestBillList(), model);
        saveBillListInSession();
        fillModel(model);
        return ViewUtil.render(request, model, Path.Template.BILLS2);
    }

    private List<Bill> handleQuery(List<Bill> billList, HashMap<String, Object> model) {
        if(thereIsClassQuery()){
            if (classEqualsReceived()) {
                model.put("received", true);
                return filterByReceivedBills(billList);
            }
        }
        return filterByIssuedBills(billList);
    }

    private boolean thereIsClassQuery() {
        return request.queryParams("class")!=null;
    }

    private boolean classEqualsReceived() {
        return request.queryParams("class").equals("received");
    }

    private List<Bill> filterByReceivedBills(List<Bill> billList) {
        return BillFilter.filterByReceived(billList, getSessionUser(request).getCompanyRFC());
    }

    private List<Bill> filterByIssuedBills(List<Bill> billList) {
        return BillFilter.filterByIssued(billList,getSessionUser(request).getCompanyRFC());
    }

    private void saveBillListInSession() {
        request.session().attribute("billList",billList);
        request.session().attribute("currentBillList",billList);
    }

    private void fillModel(HashMap<String, Object> model) {
        model.put("bills", reducedBillList());
        addToolsToModel(model);
    }

    private void addToolsToModel(HashMap<String, Object> model) {
        model.put("numberTool", new NumberTool());
        model.put("dateTool", new DateTool());
    }

    private List<Bill> reducedBillList() {
        return billList.subList(0,30);
    }

    private List<Bill> getRequestBillList() {
        return new BillsDao(getSessionUser(request).getCompanyRFC()).getAllBills();
    }


}
