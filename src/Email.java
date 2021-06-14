
/*
 * This entire class is adapted from a class of the same name in my (Alex Rattigan) Software Engineering coursework
 */


/*
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
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {

    private static String username;
    private static String password;

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

    // Get the login information for the Email from a local file
    public static void login() {

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader("email.mfp"));
            String[] split = bufferedReader.readLine().split(",");
            username = split[0];
            password = split[1];

        } catch (Exception e) {

            System.out.println("ERROR: email.mfp could not be read.");
            System.exit(1);

        }

    }

    public Email(String recipient, String subject, String message) {

        String senderUsername = username;
        String senderPassword = password;

        this.sendEmail(senderUsername, senderPassword, recipient, subject, message);

    }


    // TESTING
    public static void main(String[] args) {

        login();

        String recipient = username;

        int randInt = ((int)(Math.random() * 899999) + 100000);
        String message = "A random 6 digit number: " + randInt;

        new Email(recipient, "Test email", message);

    }
}