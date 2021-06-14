
/*
 * This entire class is adapted from a class of the same name in my (Alex Rattigan) Software Engineering coursework
 */


/****************************************
 *
 * File:        Email.java
 *
 * Date:        10/06/2021
 *
 * Author:      Alex Rattigan
 *
 * Description: Provides a way to send an Email to a specified recipient Email address. This is only included as a
 *              prototype. In the real deployed program, this would be replaced by an SMS system to better meet the
 *              conditions of the infrastructure where it is designed for.
 *
 ***************************************/

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    //Most of this code adapted from https://stackoverflow.com/questions/9086420/using-javamail-to-send-from-hotmail
    private void sendEmail(String senderUsername, String senderPassword, String recipient, String subject,
                           String messageString) {

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
                        senderUsername, senderPassword);// Specify the Username and the Password
            }
        });

        try {
            // Create MimeMessage object, add sender, recipients, subject, and message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderUsername));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(messageString);

            // Send the message
            Transport.send(message);

        } catch (MessagingException e) {

            System.out.println("ERROR: Sending email to " + recipient + ".");

        }
    }

    public Email(String recipient, String subject, String message) {

        // Very insecure to have this hard-coded but this is just a prototype program
        String senderUsername = "info@myfishingpal.ddns.net";
        String senderPassword = "password1";

        this.sendEmail(senderUsername, senderPassword, recipient, subject, message);

    }


    // TESTING
    public static void main(String[] args) {

        String recipient = "test@alexrattigan.co.uk";

        int randInt = ((int)(Math.random() * 899999) + 100000);
        String message = "A random 6 digit number: " + randInt;

        new Email(recipient, "Test email", message);

    }
}