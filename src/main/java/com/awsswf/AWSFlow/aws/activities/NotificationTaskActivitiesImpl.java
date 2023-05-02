package com.awsswf.AWSFlow.aws.activities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificationTaskActivitiesImpl implements NotificationTaskActivities {

    @Override
    public String sendNotification(String message, String recipient, String result) {
        try {

            final String username = "omkarugale057@gmail.com";
        final String password = "dbojakpamoaptlxq";
 
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
 
            Message sendMessage = new MimeMessage(session);
            sendMessage.setFrom(new InternetAddress("omkarugale057@gmail.com"));
            sendMessage.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse("maheshpimple2002@gmail.com"));
                sendMessage.setSubject("Test email");
                sendMessage.setText("This is a test email sent using JavaMail API.");
 
            Transport.send(sendMessage);
 
            System.out.println("Email sent successfully.");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
            // using SES
            // SendEmailRequest request = new SendEmailRequest()
            //         .withSource("pimpalemahesh2021@gmail.com")
            //         .withDestination(new Destination().withToAddresses("maheshpimple2002@gmail.com"))
            //         .withMessage(new Message()
            //                 .withSubject(new Content().withData("Test email"))
            //                 .withBody(new Body().withHtml(new Content().withData("<h1>Hello!</h1>"))));

            // Config.getSES().sendEmail(request);


            // using SNS
            // String phoneNumber = "9653652759";
            // PublishRequest publishRequest = new PublishRequest();
            // publishRequest.setMessage("Test message");
            // publishRequest.setPhoneNumber(phoneNumber);

            // PublishResult publishResult = Config.getSNS().publish(publishRequest);
            // System.out.println("SMS sent to " + phoneNumber + " with message Test message" + "public Result : " + publishResult);

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Notification Task Completed successfully";
    }
    
}

