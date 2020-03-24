package View.senders;


import Model.Bills.Bill;

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

public class SMTPMailSender{


    public void send(Bill bill, String mailTo, String userRFC) throws MessagingException, IOException {

        final String user="keyfinderifttt@gmail.com";
        final String password="keyfinder1234";
        String from= "";
        if (userRFC.equals(bill.getIssuerRFC())){
            from=bill.getIssuerName();
        } else {
            from=bill.getReceiverName();
        }
        String msg = "You have received a bill from: + " + from + "\n";


        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));
            message.setSubject("FinBook Notification - You have received a bill from " + from);
            File file1 = new File(bill.getUUID() + ".xml");
            file1.createNewFile();
            Path path = Paths.get(file1.getPath());
            BufferedWriter writer = Files.newBufferedWriter(path);
            writer.write(bill.getXmlFile());
            writer.close();
            DataSource source = new FileDataSource(file1);
            message.setDataHandler(new DataHandler(source));
            message.setFileName(file1.getName());

            //send the message
            Transport.send(message);
            file1.delete();

            System.out.println("Message sent successfully!");
        } catch (SendFailedException e){
            System.out.println("Invalid Address");
            throw e;
        } catch (MessagingException e) {
            e.printStackTrace();
            throw e;
        } catch(Exception e){
            throw e;
        }
    }



}
