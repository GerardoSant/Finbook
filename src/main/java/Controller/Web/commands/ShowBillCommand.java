package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.controllers.LoginController;
import Model.Bills.Bill;
import Model.Location.Location;
import View.daos.BillsDao;
import Implementations.GeoNamesLocationLoader;
import Controller.Web.webutils.Path;
import Controller.Web.webutils.ViewUtil;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.apache.velocity.tools.generic.NumberTool;

import java.util.HashMap;
import java.util.Map;

import static Controller.Web.webutils.RequestUtil.*;

public class ShowBillCommand extends FrontCommand {

    Bill bill;
    Location location;

    @Override
    public String execute() {
        LoginController.ensureUserIsLoggedIn(request, response);
        bill = getRequestedBill();
        location = loadLocation();
        return ViewUtil.render(request, model(), Path.Template.BILLS_ONE);
    }


    private Bill getRequestedBill() {
        return new BillsDao(getSessionUser(request).getCompanyRFC()).getBillByUUID(getParamUUID(request));
    }

    private Location loadLocation() {
        return new GeoNamesLocationLoader().load(bill.getPC(), "ES");
    }

    private Map model() {
        HashMap<String, Object> model = new HashMap<>();
        fillModel(model);
        cleanIfRedirected(model);
        addToolsToModel(model);
        return model;
    }

    private void fillModel(HashMap<String, Object> model) {
        model.put("bill", bill);
        model.put("location", location);
    }


    private void cleanIfRedirected(HashMap<String, Object> model) {
        model.put("redirected", removeSessionAttrLoginRedirect(request));
        model.put("emailSent", removeSessionAttrEmailSent(request));
    }


    private void addToolsToModel(HashMap<String, Object> model) {
        model.put("dateTool", new DateTool());
        model.put("numberTool", new NumberTool());
    }


}
