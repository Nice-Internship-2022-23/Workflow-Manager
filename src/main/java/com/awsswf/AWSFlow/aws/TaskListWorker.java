package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;


import com.awsswf.AWSFlow.model.Task;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;

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

public class TaskListWorker {
    private NotificationTaskActivitiesClient notificationClient = new NotificationTaskActivitiesClientImpl();    
    private HumanTaskActivitiesClient humanTaskActivitiesClient = new HumanTaskActivitiesClientImpl();
    private AutomatedTaskActivitiesClient automatedTaskActivitiesClient = new AutomatedTaskActivitiesClientImpl();
    private DependencyTaskActivitiesClient dependencyTaskActivitiesClient = new DependencyTaskActivitiesClientImpl();
    private TimerTaskActivitiesClient timerTaskActivitiesClient = new TimerTaskActivitiesClientImpl();

    Promise<String> result;
    public String performTasks(String stageName, ArrayList<Task> taskList){
            for(Task task : taskList){
                switch (task.getTaskName()) {
                    case "Notification":
                        result = notificationClient.sendNotification(stageName, stageName);
                        System.out.println("Notification task result " + result);
                        break;

                    case "Timer":
                        result = timerTaskActivitiesClient.performTimerTask();
                        System.out.println("Timer task result " + result);
                        break;

                    case "Automated":
                        result = automatedTaskActivitiesClient.performAutomatedTask();
                        System.out.println("Automated task result " + result);
                        break;

                    case "Dependency":
                        result = dependencyTaskActivitiesClient.performDependencyTask();
                        System.out.println("Dependency task result " + result);
                        break;

                    case "Human":
                        result = humanTaskActivitiesClient.performHumanTask();
                        System.out.println("Human task result " + result);
                        break;

                    default:
                        return "Empty taskList";

                }
            }
            return "All tasks in " + stageName + " performed";
    }
}
