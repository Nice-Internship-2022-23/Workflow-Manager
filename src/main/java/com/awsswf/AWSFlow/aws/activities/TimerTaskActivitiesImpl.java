package com.awsswf.AWSFlow.aws.activities;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class TimerTaskActivitiesImpl implements TimerTaskActivities {
    
    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void performTimerTask() {
        System.out.println("Peforming Timer task...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // perform timer task
    }
    
}