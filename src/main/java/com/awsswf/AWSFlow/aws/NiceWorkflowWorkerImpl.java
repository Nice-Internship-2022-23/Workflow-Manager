package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;
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

public class NiceWorkflowWorkerImpl implements NiceWorkflowWorker {

    private NotificationTaskActivitiesClient notificationTaskActivitiesClient = new NotificationTaskActivitiesClientImpl();
    private HumanTaskActivitiesClient humanTaskActivitiesClient = new HumanTaskActivitiesClientImpl();
    private AutomatedTaskActivitiesClient automatedTaskActivitiesClient = new AutomatedTaskActivitiesClientImpl();
    private DependencyTaskActivitiesClient dependencyTaskActivitiesClient = new DependencyTaskActivitiesClientImpl();
    private TimerTaskActivitiesClient timerTaskActivitiesClient = new TimerTaskActivitiesClientImpl();

    @Override
    public void initiateWorkflow(String workflowID, WorkflowExecution workflowExecution) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/stageTaskList/";

        String apiUrl = url + workflowID;

        ParameterizedTypeReference<Map<String, ArrayList<Task>>> responseType = new ParameterizedTypeReference<Map<String, ArrayList<Task>>>() {
        };

        ResponseEntity<Map<String, ArrayList<Task>>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET,
                null,
                responseType);
        Map<String, ArrayList<Task>> stageList = responseEntity.getBody();

        for (Map.Entry<String, ArrayList<Task>> entry : stageList.entrySet()) {
            String stageName = entry.getKey();
            ArrayList<Task> tasks = entry.getValue();
            Promise<String> promise = new Settable<String>("First Activity");
            for (Task task : tasks) {
                switch (task.getTaskName()) {
                    case "Notification":
                        System.out.println("Notification called");
                        promise = notificationTaskActivitiesClient.sendNotification(workflowID, url, "", promise);
                        System.out.println(promise);
                        break;
                    case "Timer":
                        System.out.println("Timer called");
                        promise = timerTaskActivitiesClient.performTimerTask("", promise);
                        System.out.println(promise);
                        break;
                    case "Automated":
                        System.out.println("Automated called");
                        promise = automatedTaskActivitiesClient.performAutomatedTask("", promise);
                        System.out.println(promise);
                        break;
                    case "Dependency":
                        System.out.println("Dependency called");
                        promise = dependencyTaskActivitiesClient.performDependencyTask("", promise);
                        System.out.println(promise);
                        break;
                    case "Human":
                        System.out.println("Human called");
                        promise = humanTaskActivitiesClient.performHumanTask("", promise);
                        System.out.println(promise);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown task type: " + task.getTaskName());
                }
            }
            System.out.println("Tasks in " + stageName + " completed");
        }

        System.out.println("All tasks are completed.");
    }

}
