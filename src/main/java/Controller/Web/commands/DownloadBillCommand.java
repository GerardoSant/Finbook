package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Model.Bills.Bill;
import View.daos.BillsDao;

import static Controller.Web.controllers.LoginController.ensureUserIsLoggedIn;
import static Controller.Web.webutils.RequestUtil.getParamUUID;
import static Controller.Web.webutils.RequestUtil.getSessionUser;

public class DownloadBillCommand extends FrontCommand {
    @Override
    public String execute() {
        ensureUserIsLoggedIn(request, response);
        response.type("text/xml");
        return billInXML();
    }

    private String billInXML() {
        Bill requestedBill = getRequestedBill();
        return requestedBill.getXmlFile();
    }

    private Bill getRequestedBill() {
        return new BillsDao(getSessionUser(request).getCompanyRFC()).getBillByUUID(getParamUUID(request));
    }
}
