package com.javanative.demo.util;

import com.javanative.demo.model.Email;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaNativeEmailSender {

    private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public void sendEmail(Email email) {
        // Email configuration
        String to = email.getTo();
        String from = email.getFrom();
        String subject = email.getSubject();
        String body = email.getBody();
        String host = email.getHost();
        String port = email.getPort();
        String htmlContent = email.getHtmlContent();

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "xxxxxxxxxxxx");
            }
        });

        // Enable debug mode for diagnostics
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject(subject, "UTF-8");

            // Now set the actual message //Ignoring this as we are sending HTML
            //message.setText(body);

            // Create the message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Set the message text and the header information for the email
            messageBodyPart.setContent(htmlContent, "text/html; charset=utf-8");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Set the multipart message to the email message
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            log.log(Level.FINE, "Email sent successfully....");
        } catch (MessagingException mex) {
            log.log(Level.SEVERE, "Could not send email successfully: " + mex.getMessage(), mex);
        }
    }
}