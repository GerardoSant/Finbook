package Controller.Web.commands;

import Controller.Web.FrontCommand;
import Controller.Web.controllers.LoginController;
import Model.Bills.Bill;
import View.daos.BillsDao;
import View.senders.SMTPMailSender;

import javax.mail.MessagingException;
import java.io.IOException;

import static Controller.Web.webutils.RequestUtil.*;

public class SendBillByEmailCommand extends FrontCommand {

    @Override
    public String execute() {
        LoginController.ensureUserIsLoggedIn(request, response);
        try {
            sendBillByEmail(getBillToSent());
        } catch (Exception e) {
        }
        redirectToBillView();
        return null;
    }

    private void sendBillByEmail(Bill bill) throws MessagingException, IOException {
        new SMTPMailSender().sendBillByMail(getParamEmail(request), bill, getSessionUser(request));
        request.session().attribute("emailSent", true);
    }

    private Bill getBillToSent() {
        return new BillsDao(getSessionUser(request).getCompanyRFC()).getBillByUUID(getParamUUID(request));
    }

    private void redirectToBillView() {
        request.session().attribute("redirected", true);
        response.redirect("/bills/" + getParamUUID(request));
    }




}
