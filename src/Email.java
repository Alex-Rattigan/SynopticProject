
/*
 * This entire class is adapted from a class of the same name in my (Alex Rattigan) Software Engineering coursework
 */

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    //Most of this code adapted from https://stackoverflow.com/questions/9086420/using-javamail-to-send-from-hotmail
    public void sendEmail(String from, String password, String to, String sub, String msg) {

        // Setup mail server
        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.yandex.ru");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Authentication
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        from, password);// Specify the Username and the PassWord
            }
        });

        try {
            // Create MimeMessage object, add sender, recipients, subject, and message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);

            // Send the message
            Transport.send(message);

        } catch (MessagingException mex) {

            System.out.println("ERROR: Sending email to " + to + ".");
            mex.printStackTrace();

        }
    }

    public Email(String recipient, String subject, String message) {

        // Very insecure to have this hard-coded but this is just a prototype program
        String sender = "info@myfishingpal.ddns.net";
        String senderPwd = "password1";

        this.sendEmail(sender, senderPwd, recipient, subject, message);

    }


    // TESTING
    public static void main(String[] args) {

        String recipient = "test@alexrattigan.co.uk";

        int randInt = ((int)(Math.random() * 899999) + 100000);
        String message = "A random number: " + randInt;

        new Email(recipient, "Test email", message);

    }
}