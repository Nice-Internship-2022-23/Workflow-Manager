package com.awsswf.AWSFlow.aws;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.awsswf.AWSFlow.config.Config;

public class NiceActivityWorkerImpl implements NiceActivityWorker {

    @Override
    public String performNotificationTaskActivity(String message, String receipant, String result) {
        try {

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

    @Override
    public String performHumanTaskActivity(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Human Task Completed successfully";
    }

    @Override
    public String performAutomatedTaskActivity(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Automated Task Completed successfully";
    }

    @Override
    public String performDependencyTaskActivity(String result) {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Dependency Task Completed successfully";
    }

    @Override
    public String performTimerTaskActivity(String result, Long time) {
        
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return "Timer Task Completed successfully";
    }

    @Override
    public String performStageTaskActivity(String stage, String result) {
        System.out.println("Task in stage " + stage + " completed.");
        return "Task in stage " + stage;
    }

}
