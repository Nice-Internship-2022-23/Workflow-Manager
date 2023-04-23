package com.awsswf.AWSFlow.aws.activities;

import java.util.ArrayList;

import com.awsswf.AWSFlow.aws.NiceChildWorkerClientExternal;
import com.awsswf.AWSFlow.aws.NiceChildWorkerClientExternalFactory;
import com.awsswf.AWSFlow.aws.NiceChildWorkerClientExternalFactoryImpl;
import com.awsswf.AWSFlow.config.MySWFClient;
import com.awsswf.AWSFlow.model.Task;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;

public class StageTaskActivitiesImpl implements StageTaskActivities {

    private NotificationTaskActivitiesClient notificationTaskActivitiesClient = new NotificationTaskActivitiesClientImpl();
    private HumanTaskActivitiesClient humanTaskActivitiesClient = new HumanTaskActivitiesClientImpl();
    private AutomatedTaskActivitiesClient automatedTaskActivitiesClient = new AutomatedTaskActivitiesClientImpl();
    private DependencyTaskActivitiesClient dependencyTaskActivitiesClient = new DependencyTaskActivitiesClientImpl();
    private TimerTaskActivitiesClient timerTaskActivitiesClient = new TimerTaskActivitiesClientImpl();

    NiceChildWorkerClientExternalFactory factory = new NiceChildWorkerClientExternalFactoryImpl(MySWFClient.getSWF(), MySWFClient.DOMAIN);

    Promise<String> result;

    @Override
    public String performActivities(String stageName, ArrayList<Task> taskList) {

        // directaly invoking activities for every task.
        // for (Task task : taskList) {
        //     switch (task.getTaskName()) {
        //         case "Notification":
        //             result = notificationTaskActivitiesClient.sendNotification(stageName, stageName);
        //             System.out.println(result);
        //             sleep(1000);
        //             break;

        //         case "Timer":
        //             result = timerTaskActivitiesClient.performTimerTask();
        //             System.out.println(result);
        //             sleep(1000);
        //             break;

        //         case "Automated":
        //             result = automatedTaskActivitiesClient.performAutomatedTask();
        //             System.out.println(result);
        //             sleep(1000);
        //             break;

        //         case "Dependency":
        //             result = dependencyTaskActivitiesClient.performDependencyTask();
        //             System.out.println(result);
        //             sleep(1000);
        //             break;

        //         case "Human":
        //             result = humanTaskActivitiesClient.performHumanTask();
        //             System.out.println(result);
        //             sleep(1000);
        //             break;

        //         default:
        //             System.out.println(task.getTaskName());

        //     }

        // }
        


        // starting new workflow for every tasklist.
        NiceChildWorkerClientExternal childWorker = factory.getClient();
        childWorker.performActivities(stageName, taskList);
        
        return "Tasks in " + stageName + " are performed successfully";

    }

    private void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
