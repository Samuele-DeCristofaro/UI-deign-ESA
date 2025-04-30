package com.esa.moviestar.Login;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailService {
    private String senderEmail;
    private String senderPassword;
    private final Properties emailProperties;
    private Session mailSession;

    public EmailService() {
        senderEmail = "moviestar@gmail.com";
        senderPassword = "eenu rbsi obnl hzha";
        this.emailProperties = new Properties();

        emailProperties.put("mail.smtp.host", "smtp.gmail.com");

        emailProperties.put("mail.smtp.port", "465");

        emailProperties.put("mail.smtp.auth", "true");

        emailProperties.put("mail.smtp.ssl.enable", "true");

        this.mailSession = Session.getInstance(emailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }
    public void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        try {
            MimeMessage message = new MimeMessage(this.mailSession);

            message.setFrom(new InternetAddress(this.senderEmail));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

            message.setSubject(subject);


            message.setText(body);

            Transport.send(message);

            System.out.println("Email inviata con successo a " + recipientEmail);

        } catch (MessagingException e) {
            System.err.println("Errore durante l'invio dell'email a " + recipientEmail + ": " + e.getMessage());
            // Rilancia l'eccezione per gestirla a un livello superiore
            throw e;
        }
    }
}
