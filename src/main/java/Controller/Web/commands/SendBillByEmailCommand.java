package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.login.LoginController;
import Model.Bills.Bill;
import View.daos.BillsDao;
import View.senders.SMTPMailSender;

import java.util.HashMap;

import static Controller.Web.webutils.RequestUtil.*;

public class SendBillByEmailCommand extends FrontCommand {

    @Override
    public String process() {
        LoginController.ensureUserIsLoggedIn(request,response);
        HashMap<String, Object> model = new HashMap<>();
        Bill bill = new BillsDao(getSessionUser(request).getCompanyRFC()).getBillByUUID(getParamUUID(request));
        model.put("bill", bill);
        request.session().attribute("redirected", true);
        try {
            new SMTPMailSender().sendBillByMail(getParamEmail(request), bill, getSessionUser(request));
            request.session().attribute("emailSent", true);
        } catch (Exception e) {
        }
        response.redirect("/bills/" + getParamUUID(request));
        return null;
    }
}
