package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.controllers.LoginController;
import Model.Bills.Bill;
import View.daos.BillsDao;
import Implementations.GeoNamesLocationLoader;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import org.apache.velocity.tools.generic.DateTool;

import java.util.HashMap;

import static Controller.Web.webutils.RequestUtil.*;

public class ShowBillCommand extends FrontCommand {
    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        Bill bill = new BillsDao(getSessionUser(request).getCompanyRFC()).getBillByUUID(getParamUUID(request));
        model.put("bill", bill);
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        model.put("emailSent", removeSessionAttrEmailSent(request));
        model.put("location", new GeoNamesLocationLoader().load(bill.getPC(), "ES"));
        model.put("dateTool", new DateTool());
        return ViewUtil.render(request, model, Path.Template.BILLS_ONE);
    }
}
