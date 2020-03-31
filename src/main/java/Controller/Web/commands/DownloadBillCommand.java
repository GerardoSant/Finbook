package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.login.LoginController;
import Model.Bills.Bill;
import View.daos.BillsDao;

import static Controller.util.RequestUtil.getParamUUID;

public class DownloadBillCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request, response);
        response.type("text/xml");
        StringBuilder sb = new StringBuilder();
        Bill bill = new BillsDao(request.session().attribute("currentUser")).getBillByUUID(getParamUUID(request));
        sb.append(bill.getXmlFile());
        return sb.toString();
    }
}
