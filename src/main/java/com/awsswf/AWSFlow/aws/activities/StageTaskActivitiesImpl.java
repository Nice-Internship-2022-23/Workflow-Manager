package com.awsswf.AWSFlow.aws.activities;

import java.util.ArrayList;

import com.awsswf.AWSFlow.model.Task;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;

public class StageTaskActivitiesImpl implements StageTaskActivities {

    private NotificationTaskActivitiesClient notificationTaskActivitiesClient = new NotificationTaskActivitiesClientImpl();
    private HumanTaskActivitiesClient humanTaskActivitiesClient = new HumanTaskActivitiesClientImpl();
    private AutomatedTaskActivitiesClient automatedTaskActivitiesClient = new AutomatedTaskActivitiesClientImpl();
    private DependencyTaskActivitiesClient dependencyTaskActivitiesClient = new DependencyTaskActivitiesClientImpl();
    private TimerTaskActivitiesClient timerTaskActivitiesClient = new TimerTaskActivitiesClientImpl();

    Promise<String> result = null;

    @Override
    public String performActivities(String stageName, ArrayList<Task> taskList) {

        // return childWorker.getWorkflowExecution();

        // directaly invoking activities for every task.
        // for (Task task : taskList) {
        //     switch (task.getTaskName()) {
        //         case "Notification":
        //             result = notificationTaskActivitiesClient.sendNotification(stageName,
        //                     stageName);
        //             System.out.println(result);
        //             break;

        //         case "Timer":
        //             result = timerTaskActivitiesClient.performTimerTask();
        //             System.out.println(result);
        //             break;

        //         case "Automated":
        //             result = automatedTaskActivitiesClient.performAutomatedTask();
        //             System.out.println(result);
        //             break;

        //         case "Dependency":
        //             result = dependencyTaskActivitiesClient.performDependencyTask();
        //             System.out.println(result);
        //             break;

        //         case "Human":
        //             result = humanTaskActivitiesClient.performHumanTask();
        //             System.out.println(result);
        //             break;

        //         default:
        //             System.out.println(task.getTaskName());

        //     }

        // }

        return "Tasks in " + stageName + " are performed successfully";

    }
}
