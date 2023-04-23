package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.awsswf.AWSFlow.aws.activities.StageTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.StageTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.model.Task;

import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesClientImpl;

public class NiceChildWorkerImpl implements NiceChildWorker {
    private NotificationTaskActivitiesClient notificationClient = new NotificationTaskActivitiesClientImpl();    
    private HumanTaskActivitiesClient humanTaskActivitiesClient = new HumanTaskActivitiesClientImpl();
    private AutomatedTaskActivitiesClient automatedTaskActivitiesClient = new AutomatedTaskActivitiesClientImpl();
    private DependencyTaskActivitiesClient dependencyTaskActivitiesClient = new DependencyTaskActivitiesClientImpl();
    private TimerTaskActivitiesClient timerTaskActivitiesClient = new TimerTaskActivitiesClientImpl();

    Promise<String> result = null;
    @Override
    public void performActivities(String stageName, ArrayList<Task> taskList) {
        for(Task task : taskList){
            switch (task.getTaskName()) {
                case "Notification":
                    result = notificationClient.sendNotification(stageName, stageName);
                    break;

                case "Timer":
                    result = timerTaskActivitiesClient.performTimerTask();
                    break;

                case "Automated":
                    result = automatedTaskActivitiesClient.performAutomatedTask();
                    break;

                case "Dependency":
                    result = dependencyTaskActivitiesClient.performDependencyTask();
                    break;

                case "Human":
                    result = humanTaskActivitiesClient.performHumanTask();
                    break;

                default:
                    System.out.println("Empty Task List");

            }
        }
        System.out.println("All tasks in " + stageName + " performed");
    }

}
