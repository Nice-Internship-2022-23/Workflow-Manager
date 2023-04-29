package com.awsswf.AWSFlow.service;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.awsswf.AWSFlow.aws.NiceWorkflowWorkerClientExternal;
import com.awsswf.AWSFlow.aws.NiceWorkflowWorkerClientExternalFactory;
import com.awsswf.AWSFlow.aws.NiceWorkflowWorkerClientExternalFactoryImpl;
import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.AutomatedTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.DependencyTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.HumanTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.NotificationTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesClient;
import com.awsswf.AWSFlow.aws.activities.TimerTaskActivitiesClientImpl;
import com.awsswf.AWSFlow.config.MySWFClient;
import com.awsswf.AWSFlow.model.Task;

@Service
public class workflowManagerServiceImpl implements workflowManagerService{

    AmazonSimpleWorkflow service = MySWFClient.getSWF();
        String domain = MySWFClient.DOMAIN;
        String taskList = MySWFClient.TASKLIST;
        String executionId = MySWFClient.getExecutionId();
    Map<String, String> response = null;

    private NotificationTaskActivitiesClient notificationTaskActivitiesClient = new NotificationTaskActivitiesClientImpl();
    private HumanTaskActivitiesClient humanTaskActivitiesClient = new HumanTaskActivitiesClientImpl();
    private AutomatedTaskActivitiesClient automatedTaskActivitiesClient = new AutomatedTaskActivitiesClientImpl();
    private DependencyTaskActivitiesClient dependencyTaskActivitiesClient = new DependencyTaskActivitiesClientImpl();
    private TimerTaskActivitiesClient timerTaskActivitiesClient = new TimerTaskActivitiesClientImpl();
        
    @Override
    public Map<String, String> startWorkflow(String workflowId) {
        
        NiceWorkflowWorkerClientExternalFactory factory = new NiceWorkflowWorkerClientExternalFactoryImpl(service, domain);
        NiceWorkflowWorkerClientExternal worker = factory.getClient(executionId);
        worker.initiateWorkflow(workflowId, worker.getWorkflowExecution());

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/stageTaskList/";

        String apiUrl = url + workflowId;

        ParameterizedTypeReference<Map<String, ArrayList<Task>>> responseType = new ParameterizedTypeReference<Map<String, ArrayList<Task>>>() {
        };

        ResponseEntity<Map<String, ArrayList<Task>>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET,
                null,
                responseType);

        Map<String, ArrayList<Task>> stageList = responseEntity.getBody();

        Promise<String> result = null;
        for(int i = 0; i < stageList.size(); i++){
            
            ArrayList<Task> taskList = stageList.get(0);

            for(Task task : taskList){
                switch (task.getTaskName()) {
                    // case "Notification":
                    //     result = notificationTaskActivitiesClient.sendNotification("","");
                    //     System.out.println(result);
                    //     break;
    
                    // case "Timer":
                    //     result = timerTaskActivitiesClient.performTimerTask();
                    //     System.out.println(result);
                    //     break;
    
                    // case "Automated":
                    //     result = automatedTaskActivitiesClient.performAutomatedTask();
                    //     System.out.println(result);
                    //     break;
    
                    // case "Dependency":
                    //     result = dependencyTaskActivitiesClient.performDependencyTask();
                    //     System.out.println(result);
                    //     break;
    
                    // case "Human":
                    //     result = humanTaskActivitiesClient.performHumanTask();
                    //     System.out.println(result);
                    //     break;
    
                    // default:
                    //     System.out.println(task.getTaskName());
    
                }
            }
        }

        return response;
    }
    
}
