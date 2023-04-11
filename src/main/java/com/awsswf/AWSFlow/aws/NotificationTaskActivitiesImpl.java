package com.awsswf.AWSFlow.aws;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class NotificationTaskActivitiesImpl implements NotificationTaskActivities {

    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void sendNotification(String message, String recipient) {
        System.out.println("Peforming Notification task...");
        // Send notification here
    }
    
}

