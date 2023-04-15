package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivities;
import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivities;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivities;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivities;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivities;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesImpl;
import com.awsswf.AWSFlow.model.Task;

public class NiceWorkflowWorkerImpl implements NiceWorkflowWorker {

    @Override
    public void initiateWorkflow(String workflowID) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/stageTaskList/";

        String apiUrl = url + workflowID;

        ParameterizedTypeReference<Map<String, ArrayList<Task>>> responseType = new ParameterizedTypeReference<Map<String, ArrayList<Task>>>() {
        };

        ResponseEntity<Map<String, ArrayList<Task>>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET,
                null,
                responseType);
        Map<String, ArrayList<Task>> response = responseEntity.getBody();

        for(String key : response.keySet()){
            System.out.println(key);
            for(Task task : response.get(key)){
                switch (task.getTaskName()) {
                    case "Notification":
                        NotificationTaskActivities notificationTaskActivities = new NotificationTaskActivitiesImpl();
                        notificationTaskActivities.sendNotification("", "");
                        System.out.println("Notification Task performed successfully.");
                        break;

                    case "Timer":
                        TimerTaskActivities timerTaskActivities = new TimerTaskActivitiesImpl();
                        timerTaskActivities.performTimerTask();
                        System.out.println("Timer Task performed successfully.");
                        break;

                    case "Automated":
                        AutomatedTaskActivities automatedTaskActivities = new AutomatedTaskActivitiesImpl();
                        automatedTaskActivities.performAutomatedTask();
                        System.out.println("Automated Task performed successfully.");
                        break;

                    case "Dependency":
                        DependencyTaskActivities dependencyTaskActivities = new DependencyTaskActivitiesImpl();
                        dependencyTaskActivities.performDependencyTask();
                        System.out.println("Dependency Task performed successfully.");
                        break;

                    case "Human":
                        HumanTaskActivities humanTaskActivities = new HumanTaskActivitiesImpl();
                        humanTaskActivities.performHumanTask();
                        System.out.println("Human Task performed successfully.");
                        break;

                    default:

                }
            }


            System.out.println("\n\t\tStage : " + key + " performed successfully");
        }

        System.out.println("All tasks in stages performed");
    }
}
