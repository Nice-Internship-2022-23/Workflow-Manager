package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesImpl;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesImpl;
import com.awsswf.AWSFlow.config.MySWFClient;

public class NiceWorker {

    private RestTemplate restTemplate = new RestTemplate();

    public String startWorkflow(String workflowId) {

        AmazonSimpleWorkflow service = MySWFClient.getSWF();
        String domain = MySWFClient.getDomain();
        String taskList = MySWFClient.getTaskList();
        String executionId = MySWFClient.getExecutionId();

        try {

            ActivityWorker aw = new ActivityWorker(service, domain, taskList);
            aw.addActivitiesImplementations(startActivities(workflowId));
            aw.start();

            WorkflowWorker wfw = new WorkflowWorker(service, domain, taskList);
            wfw.addWorkflowImplementationType(NiceWorkflowWorkerImpl.class);
            wfw.start();

            NiceWorkflowWorkerClientExternalFactory factory = new NiceWorkflowWorkerClientExternalFactoryImpl(service,
                    domain);
            NiceWorkflowWorkerClientExternal worker = factory.getClient(executionId);
            worker.initiateWorkflow(workflowId);

            String url = "http://localhost:8080/api/getWorkflowName/" + workflowId;
            ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
            };

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
                responseType);

            return responseEntity.getBody() + " Started";

        } catch (Exception e) {
            return "Error " + e;
        }

    }

    private List<Object> startActivities(String workflowID) {

        List<Object> activityList = new ArrayList<>();

        String url = "http://localhost:8080/api/getOnlyTasks/";

        String apiUrl = url + workflowID;

        ParameterizedTypeReference<ArrayList<String>> responseType = new ParameterizedTypeReference<ArrayList<String>>() {
        };

        ResponseEntity<ArrayList<String>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null,
                responseType);
        ArrayList<String> response = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();

        try {
            ArrayList<String> tasks = mapper.readValue(mapper.writeValueAsString(response),
                    new TypeReference<ArrayList<String>>() {
                    });

            for (String task : tasks) {
                switch (task) {
                    case "Notification":
                        activityList.add(new NotificationTaskActivitiesImpl());
                        System.out.println("Notification Activity Worker started");
                        break;

                    case "Timer":
                        activityList.add(new TimerTaskActivitiesImpl());
                        System.out.println("Timer Activity Worker started");
                        break;

                    case "Automated":
                        activityList.add(new AutomatedTaskActivitiesImpl());
                        System.out.println("Automated Activity Worker started");
                        break;

                    case "Dependency":
                        activityList.add(new DependencyTaskActivitiesImpl());
                        System.out.println("Dependency Activity Worker started");
                        break;

                    case "Human":
                        activityList.add(new HumanTaskActivitiesImpl());
                        System.out.println("Human Activity Worker started");
                        break;

                    default:
                        return activityList;
                }
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return activityList;
    }
}
