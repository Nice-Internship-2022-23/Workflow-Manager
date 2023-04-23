package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.flow.DecisionContext;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClock;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.awsswf.AWSFlow.aws.activities.StageTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.StageTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.config.MySWFClient;
import com.awsswf.AWSFlow.model.Task;

public class NiceWorkflowWorkerImpl implements NiceWorkflowWorker {

    private StageTaskActivitiesClient stageTaskActivitiesClient = new StageTaskActivitiesClientImpl();

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
        Promise<String> res = null;

        NiceChildWorkerClientExternalFactory factory = new NiceChildWorkerClientExternalFactoryImpl(
                MySWFClient.getSWF(), MySWFClient.DOMAIN);
        

        for (String key : response.keySet()) {

            System.out.println("\n\t\t" + key);

            // invoking through stage Activity
            res = stageTaskActivitiesClient.performActivities(key, response.get(key));
            System.out.println(res);

            // using TaskListWorker object.
            // String result = new TaskListWorker().performTasks(key, response.get(key));
            // System.out.println(result);

            // using Child worker
            // NiceChildWorkerClientExternal childWorker = factory.getClient();
            // childWorker.performActivities(key, response.get(key));
        }

        System.out.println("All tasks in all stages performed");

    }
}
