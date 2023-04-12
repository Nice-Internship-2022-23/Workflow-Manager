package com.awsswf.AWSFlow.aws.activities;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class DependencyTaskActivitiesImpl implements DependencyTaskActivities{

    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void performDependencyTask() {
        System.out.println("Peforming Dependency task...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // perform dependency task
    }
    
}
