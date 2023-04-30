package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;
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
        Map<String, ArrayList<Task>> stageList = responseEntity.getBody();

        NiceActivityWorkerClient client = new NiceActivityWorkerClientImpl();

        Promise<Long> timer = new Settable<Long>(400L);
            Promise<String> result = new Settable<String>("First");
        for(Map.Entry<String, ArrayList<Task>> entry: stageList.entrySet()){
            for(Task task : entry.getValue()){
                if(task.getTaskName().equals("Notification")){
                    result = client.performNotificationTaskActivity(result, result, result, result); 
                } else if(task.getTaskName().equals("Timer")){
                    result = client.performTimerTaskActivity(result, timer, result);
                } else if(task.getTaskName().equals("Human")){
                    result = client.performHumanTaskActivity(result, result);
                } else if(task.getTaskName().equals("Dependency")){
                    result = client.performDependencyTaskActivity(result, result);
                } else if(task.getTaskName().equals("Automated")){
                    result = client.performAutomatedTaskActivity(result, result);
                }
            }
        }

        System.out.println("All tasks are completed.");
    }

}
