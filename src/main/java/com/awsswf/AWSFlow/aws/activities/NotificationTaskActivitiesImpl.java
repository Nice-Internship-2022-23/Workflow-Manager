package com.awsswf.AWSFlow.aws.activities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.awsswf.AWSFlow.config.Config;

public class NotificationTaskActivitiesImpl implements NotificationTaskActivities {

    @Override
    public String sendNotification(String message, String recipient, String subject, String result) {
        try {
            if(recipient == "" || message == ""){
                return "Wrong data format to send notification please edit your workflow";
            }
            
            System.out.println("Message : " + message + " Recipient : " + recipient + " Subject : " + subject);
            final String username = Config.EMAIL_USERNAME;
            final String password = Config.EMAIL_PASSWORD;

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {
                System.out.println("Receipant : " + recipient + " Message : " + message);
                Message sendMessage = new MimeMessage(session);
                sendMessage.setFrom(new InternetAddress(Config.EMAIL_USERNAME));
                sendMessage.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipient));
                sendMessage.setSubject(subject);
                sendMessage.setText(message);

                Transport.send(sendMessage);

                System.out.println("Email sent successfully.");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Notification Task Completed successfully";
    }

}
