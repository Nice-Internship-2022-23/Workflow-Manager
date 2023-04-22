package com.awsswf.AWSFlow.aws.activities;

import java.util.ArrayList;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.amazonaws.services.simpleworkflow.flow.annotations.ManualActivityCompletion;
import com.awsswf.AWSFlow.model.Task;

@Activities(version="1.0")
@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300,
                             defaultTaskStartToCloseTimeoutSeconds = 300)
public interface StageTaskActivities {
    
    @Activity(name = "StageTask")
    @ManualActivityCompletion
    public String performActivities(String stageName, ArrayList<Task> taskList);
    
}
