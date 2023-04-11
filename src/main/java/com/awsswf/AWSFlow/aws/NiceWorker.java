package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.awsswf.AWSFlow.config.MySWFClient;
import com.awsswf.AWSFlow.model.Task;

public class NiceWorker {

    public String startWorkflow(String workflowId) {

        AmazonSimpleWorkflow service = MySWFClient.getSWF();
        String domain = MySWFClient.getDomain();
        String taskList = MySWFClient.getTaskList();
        String executionId = MySWFClient.getExecutionId();

        try {

            startActivities(workflowId);

            WorkflowWorker wfw = new WorkflowWorker(service, domain, taskList);
            wfw.addWorkflowImplementationType(NiceWorkflowWorkerImpl.class);
            wfw.start();

            NiceWorkflowWorkerClientExternalFactory factory = new NiceWorkflowWorkerClientExternalFactoryImpl(service, domain);
            NiceWorkflowWorkerClientExternal worker = factory.getClient(executionId);
            worker.initiateWorkflow(workflowId);


            return "Workflow Started";

        } catch (Exception e) {
            return "Error " + e;
        }

    }

    private String startActivities(String workflowID) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/getTasks/";

        String apiUrl = url + workflowID;

        ParameterizedTypeReference<ArrayList<Task>> responseType = new ParameterizedTypeReference<ArrayList<Task>>() {
        };

        ResponseEntity<ArrayList<Task>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                responseType);
        ArrayList<Task> response = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();

        try {
            ArrayList<Task> tasks = mapper.readValue(mapper.writeValueAsString(response),
                    new TypeReference<ArrayList<Task>>() {
                    });

            for (Task task : tasks) {
                switch (task.getTaskName()) {
                    case "Notification":
                        TaskWorker<NotificationTaskActivitiesImpl> notificationWorker = new TaskWorker<NotificationTaskActivitiesImpl>(
                                new NotificationTaskActivitiesImpl());
                        notificationWorker.startWorker();
                        System.out.println("Notification Activity Worker started");
                        break;

                    case "Timer":
                        TaskWorker<TimerTaskActivitiesImpl> timerWorker = new TaskWorker<TimerTaskActivitiesImpl>(
                                new TimerTaskActivitiesImpl());
                        timerWorker.startWorker();
                        System.out.println("Timer Activity Worker started");
                        break;

                    case "Automated":
                        TaskWorker<AutomatedTaskActivitiesImpl> automatedWorker = new TaskWorker<AutomatedTaskActivitiesImpl>(
                                new AutomatedTaskActivitiesImpl());
                        automatedWorker.startWorker();
                        System.out.println("Automated Activity Worker started");
                        break;

                    case "Dependency":
                        TaskWorker<DependencyTaskActivitiesImpl> dependencyWorker = new TaskWorker<DependencyTaskActivitiesImpl>(
                                new DependencyTaskActivitiesImpl());
                        dependencyWorker.startWorker();
                        System.out.println("Dependency Activity Worker started");
                        break;

                    case "Human":
                        TaskWorker<HumanTaskActivitiesImpl> humanWorker = new TaskWorker<HumanTaskActivitiesImpl>(
                                new HumanTaskActivitiesImpl());
                        humanWorker.startWorker();
                        System.out.println("Human Activity Worker started");
                        break;

                    default:
                        return "No Activity to run";
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Activities started";
    }
}
