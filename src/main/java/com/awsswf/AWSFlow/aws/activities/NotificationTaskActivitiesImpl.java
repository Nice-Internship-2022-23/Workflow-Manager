package com.awsswf.AWSFlow.aws.activities;

public class NotificationTaskActivitiesImpl implements NotificationTaskActivities {

    @Override
    public String sendNotification(String message, String recipient, String result) {
        System.out.println("Peforming Notification task...");
        try {
            Thread.sleep(8000);
            return "Notification Task";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage().toString();
        }
    }
    
}

