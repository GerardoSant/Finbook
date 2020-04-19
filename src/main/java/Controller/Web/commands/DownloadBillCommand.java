package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.login.LoginController;
import Model.Bills.Bill;
import View.daos.BillsDao;

import static Controller.Web.webutils.RequestUtil.getParamUUID;
import static Controller.Web.webutils.RequestUtil.getSessionUser;

public class DownloadBillCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request, response);
        response.type("text/xml");
        StringBuilder sb = new StringBuilder();
        Bill bill = new BillsDao(getSessionUser(request).getCompanyRFC()).getBillByUUID(getParamUUID(request));
        sb.append(bill.getXmlFile());
        return sb.toString();
    }
}
