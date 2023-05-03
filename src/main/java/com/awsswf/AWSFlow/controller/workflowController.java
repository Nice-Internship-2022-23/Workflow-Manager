package com.awsswf.AWSFlow.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.simpleworkflow.model.GetWorkflowExecutionHistoryRequest;
import com.amazonaws.services.simpleworkflow.model.History;
import com.amazonaws.services.simpleworkflow.model.HistoryEvent;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;
import com.awsswf.AWSFlow.aws.NiceWorker;
import com.awsswf.AWSFlow.config.Config;
import com.awsswf.AWSFlow.model.historyRequest;

@RestController
@RequestMapping("workflow")
public class workflowController {
    
    @GetMapping
    public String home(){
        return "Welcome";
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/api/{workflowId}")
    public ResponseEntity<?> startworkflow(@PathVariable String workflowId){
            Map<String, String> response = new HashMap<>();
            response = new NiceWorker().startWorkflow(workflowId);

        return ResponseEntity.ok().body(response);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/api/history")
    public ResponseEntity<?> getExecutionHistory(@RequestBody historyRequest request){
        
        History history = Config.getSWF().getWorkflowExecutionHistory(
            new GetWorkflowExecutionHistoryRequest()
                .withDomain(Config.DOMAIN)
                .withExecution(new WorkflowExecution().withWorkflowId(request.getWorkflowExecutionId()).withRunId(request.getWorkflowRunId()))
        );
        List<String> events = new ArrayList<>();
        for(HistoryEvent event : history.getEvents()){
            if(event.getEventType().equals("ActivityTaskScheduled")){
                events.add("ActivityTaskScheduled -> " + event.getActivityTaskScheduledEventAttributes().getActivityType().getName() + " Id = " + event.getActivityTaskScheduledEventAttributes().getActivityId());
            }
            else if(event.getEventType().equals("ActivityTaskCompleted")){
                events.add("ActivityTaskCompleted -> Result : " + event.getActivityTaskCompletedEventAttributes().getResult());
            }
            else if(event.getEventType().equals("WorkflowExecutionFailed")){
                events.add("WorkflowExecutionFailed -> Reason : " + event.getActivityTaskFailedEventAttributes().getReason());
            }
            else{
                events.add(event.getEventType());
            }
        }
        return ResponseEntity.ok().body(events);
    }
}
