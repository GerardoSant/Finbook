package View.senders;


import Model.Bills.Bill;
import Model.User.User;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Properties;

public class SMTPMailSender implements MailSender{

    private static final String USER = "keyfinderifttt@gmail.com";
    private static final String PASSWORD= "keyfinder1234";

    @Override
    public void sendBillByMail(String mailTo, Bill bill, User from) throws MessagingException, IOException {
        try {
            sendEmail(mailTo, bill, from);
        } catch (Exception e){
            System.out.println("Invalid address");
            throw e;
        }

    }

    private void sendEmail(String mailTo, Bill bill, User from) throws IOException, MessagingException {
        Transport.send(message(mailTo, from, billXMLFile(bill)));
        clearTemporaryFile(billXMLFile(bill)); //Once message is sended, bill file created for send is deleted.
    }

    private MimeMessage message(String mailTo, User from, File billXMLFile) throws MessagingException {
        MimeMessage message = new MimeMessage(session());
        addMessage(mailTo, from, message);
        addFile(billXMLFile, message);
        return message;
    }

    private void addMessage(String mailTo, User from, MimeMessage message) throws MessagingException {
        message.setFrom(new InternetAddress(USER));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));
        message.setSubject("FinBook Notification - You have received a bill from " + from.getCompanyName());
    }

    private void addFile(File billXMLFile, MimeMessage message) throws MessagingException {
        DataSource source = new FileDataSource(billXMLFile);
        message.setDataHandler(new DataHandler(source));
        message.setFileName(billXMLFile.getName());
    }

    private File billXMLFile(Bill bill) throws IOException {
        File billXMLFile = createFile(bill);
        writeOnFile(bill, billXMLFile);
        return billXMLFile;
    }

    private File createFile(Bill bill) throws IOException {
        File billXMLFile = new File(bill.getUUID() + ".xml");
        billXMLFile.createNewFile();
        return billXMLFile;
    }

    private void writeOnFile(Bill bill, File billXMLFile) throws IOException {
        Path path = Paths.get(billXMLFile.getPath());
        BufferedWriter writer = Files.newBufferedWriter(path);
        writer.write(bill.getXmlFile());
        writer.close();
    }

    private Session session() {
        return Session.getDefaultInstance(properties(),
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(USER,PASSWORD);
                        }
                    });
    }

    private Properties properties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        return props;
    }

    private void clearTemporaryFile(File temporaryBillFile) {
        temporaryBillFile.delete();
    }
}
