package com.awsswf.AWSFlow.aws;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 60,
                             defaultTaskStartToCloseTimeoutSeconds = 2600000)
@Activities(version="1.0")
public interface NiceActivityWorker {
    
    @Activity(name = "NotificationTaskActivity")
    public String performNotificationTaskActivity(String message, String receipant, String result);

    @Activity(name = "HumanTaskActivity")
    public String performHumanTaskActivity(String result);

    @Activity(name = "AutomatedTaskActivity")
    public String performAutomatedTaskActivity(String result);

    @Activity(name = "DependencyTaskActivity")
    public String performDependencyTaskActivity(String result);

    @Activity(name = "TimerTaskActivity")
    public String performTimerTaskActivity(String result, Long time);

    @Activity(name = "StageTaskActivity")
    public String performStageTaskActivity(String stage, String result);
}
