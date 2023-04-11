package com.awsswf.AWSFlow.aws;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class AutomatedTaskActivitiesImpl implements AutomatedTaskActivities {

    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void performAutomatedTask() {
        System.out.println("Peforming Automated task...");
        // Perform automated task here
    }
    
}

