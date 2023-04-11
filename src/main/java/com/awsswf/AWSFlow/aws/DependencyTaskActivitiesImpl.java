package com.awsswf.AWSFlow.aws;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;

public class DependencyTaskActivitiesImpl implements DependencyTaskActivities{

    @Override
    @Asynchronous
    @ExponentialRetry(initialRetryIntervalSeconds = 1, maximumAttempts = 3)
    public void performDependencyTask() {
        System.out.println("Peforming Dependency task...");
        // perform dependency task
    }
    
}
