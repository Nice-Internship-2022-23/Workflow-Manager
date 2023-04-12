package com.awsswf.AWSFlow.aws.activities;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class HumanTaskActivitiesImpl implements HumanTaskActivities {
    
    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void performHumanTask() {
        System.out.println("Peforming Human task...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform human task here
    }
    
}

