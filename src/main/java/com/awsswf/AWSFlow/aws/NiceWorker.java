package com.awsswf.AWSFlow.aws;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;

import com.awsswf.AWSFlow.config.MySWFClient;

public class NiceWorker {

    private RestTemplate restTemplate = new RestTemplate();
    private Map<String, String> response = new HashMap<>();

    public Map<String, String> startWorkflow(String workflowId) {

        AmazonSimpleWorkflow service = MySWFClient.getSWF();
        String domain = MySWFClient.DOMAIN;
        String taskList = MySWFClient.TASKLIST;
        String executionId = MySWFClient.getExecutionId();

        try {

            ActivityWorker aw = new ActivityWorker(service, domain, taskList);
            aw.addActivitiesImplementation(new NiceActivityWorkerImpl());
            aw.start();

            WorkflowWorker wfw = new WorkflowWorker(service, domain, taskList);
            wfw.addWorkflowImplementationType(NiceWorkflowWorkerImpl.class);
            wfw.start();

            NiceWorkflowWorkerClientExternalFactory factory = new NiceWorkflowWorkerClientExternalFactoryImpl(service, domain);
            NiceWorkflowWorkerClientExternal worker = factory.getClient(executionId);
            worker.initiateWorkflow(workflowId);

            String url = "http://localhost:8080/api/getWorkflowName/" + workflowId;
            ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {
            };

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
                responseType);

            String workflowExecutionId = worker.getWorkflowExecution().getWorkflowId();
            String workflowRunId = worker.getWorkflowExecution().getRunId();
            String worklfowName = responseEntity.getBody();

            response.put("EXECUTIONID", workflowExecutionId);
            response.put("RUNID", workflowRunId);
            response.put("NAME", worklfowName);
            response.put("MESSAGE", "Activity worker and Workflow Worker started");
            return response;

        } catch (Exception e) {
            response.clear();
            response.put("ERROR", e.getMessage());
            return response;
        }

    }
}
