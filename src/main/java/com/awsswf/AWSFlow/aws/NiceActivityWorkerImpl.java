package com.awsswf.AWSFlow.aws;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesImpl;

public class NiceActivityWorkerImpl implements NiceActivityWorker {

    @Override
    public String performNotificationTaskActivity(String message, String receipant, String result) {
        return new NotificationTaskActivitiesImpl().sendNotification(message, receipant, result);
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
        return new TimerTaskActivitiesImpl().TimerTask(time);
    }

    @Override
    public String performStageTaskActivity(String stage, String result) {
        System.out.println("Task in stage " + stage + " completed.");
        return "Task in stage " + stage + " completed successfully.";
    }

}
