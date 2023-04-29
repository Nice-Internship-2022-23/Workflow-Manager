package com.awsswf.AWSFlow.aws.activities;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

@Activities(version = "1.0")
@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 10, defaultTaskStartToCloseTimeoutSeconds = 60)
public interface TimerTaskActivities {
    
    @Activity(name = "TimerTask")
    public String performTimerTask(String result);
    
}

