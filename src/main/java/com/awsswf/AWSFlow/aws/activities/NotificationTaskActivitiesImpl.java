package com.awsswf.AWSFlow.aws.activities;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class NotificationTaskActivitiesImpl implements NotificationTaskActivities {

    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void sendNotification(String message, String recipient) {
        System.out.println("Peforming Notification task...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Send notification here
    }
    
}

