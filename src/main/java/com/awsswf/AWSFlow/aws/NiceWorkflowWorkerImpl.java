package com.awsswf.AWSFlow.aws;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;
import com.awsswf.AWSFlow.model.Input;
import com.awsswf.AWSFlow.model.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;

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

        Promise<Long> timer = new Settable<Long>(4000L);
        Promise<String> result = new Settable<String>("First");
        Promise<List<Input>> workflowInputPromise = null;
        for (Map.Entry<String, ArrayList<Task>> entry : stageList.entrySet()) {
            for (Task task : entry.getValue()) {
                if (task.getTaskName().equals("Notification")) {

                    workflowInputPromise = getWorkflowInputData(new Settable<String>(workflowID), new Settable<String>(entry.getKey()), result);

                    String receipant = "", message = "", subject = "No Subject";
                    for (Input input : workflowInputPromise.get()) {
                        if (input.getFieldType().equalsIgnoreCase("email")
                                || input.getFieldName().equalsIgnoreCase("email")) {
                            receipant = input.getFieldValue();
                        }
                        if (input.getFieldName().equalsIgnoreCase("Message")) {
                            message = input.getFieldValue();
                        }
                        if (input.getFieldName().equalsIgnoreCase("Subject")){
                            subject = input.getFieldValue();
                        }
                    }         
                    if(receipant == "" || receipant == null || message == "" || message == null){
                        break;
                    }           
                    Promise<String> notificationSubject = new Settable<String>(subject);
                    Promise<String> notificationMessage = new Settable<String>(message);
                    Promise<String> notificationReceiver = new Settable<String>(receipant);
                    
                    result = client.performNotificationTaskActivity(notificationMessage, notificationReceiver, notificationSubject, result, result);
                } else if (task.getTaskName().equals("Timer")) {
                    try{
                        timer = new Settable<Long>(Long.parseLong(task.getTaskParameter()));
                        System.out.println(timer.get());
                        result = client.performTimerTaskActivity(result, timer, result);
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                        break;
                    }
                    
                } else if (task.getTaskName().equals("Human")) {
                    result = client.performHumanTaskActivity(result, result);
                } else if (task.getTaskName().equals("Dependency")) {
                    result = client.performDependencyTaskActivity(result, result);
                } else if (task.getTaskName().equals("Automated")) {
                    result = client.performAutomatedTaskActivity(result, result);
                }
            }
            client.performStageTaskActivity(entry.getKey(), entry.getKey(), result, result, workflowInputPromise);
        }
    }


    Promise<List<Input>> getWorkflowInputData(Promise<String> workflowId, Promise<String> stageName, Promise<String> result){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/api/getStageInputs/" + workflowId.get() + "/" + stageName.get();

        ParameterizedTypeReference<List<String>> responseType = new ParameterizedTypeReference<List<String>>() {
        };

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                null,
                responseType);
        List<String> stageInputList = responseEntity.getBody();

        List<Input> inputList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (String input : stageInputList) {
            try {
                inputList.add(mapper.readValue(input, Input.class));
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return new Settable<List<Input>>(inputList);
    }

}
