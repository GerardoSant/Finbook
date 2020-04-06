package View.senders;

import Model.Bills.Bill;
import Model.User.User;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailSender {
    void sendBillByMail(String mailTo, Bill bill, User from) throws MessagingException, IOException;
}
