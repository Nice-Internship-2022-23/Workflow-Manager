package com.awsswf.AWSFlow.aws;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class TimerTaskActivitiesImpl implements TimerTaskActivities {
    
    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void performTimerTask() {
        System.out.println("Peforming Timer task...");
        // perform timer task
    }
    
}