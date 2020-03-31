package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.login.LoginController;
import Model.Bills.Bill;
import View.daos.BillsDao;
import View.senders.SMTPMailSender;

import java.util.HashMap;

import static Controller.util.RequestUtil.*;

public class SendBillByEmailCommand extends FrontCommand {

    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        Bill bill = new BillsDao(getSessionCurrentUser(request)).getBillByUUID(getParamUUID(request));
        model.put("bill", bill);
        request.session().attribute("redirected", true);
        try {
            new SMTPMailSender().send(bill, getParamEmail(request), getSessionCurrentUser(request));
            request.session().attribute("emailSent", true);
        } catch (Exception e) {
        }
        response.redirect("/bills/" + getParamUUID(request));
        return null;
    }
}
