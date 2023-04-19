package com.awsswf.AWSFlow.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awsswf.AWSFlow.aws.NiceWorker;

@RestController
@RequestMapping("workflow")
public class workflowController {
    
    @GetMapping
    public String home(){
        return "Welcome";
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/api/{workflowId}")
    public ResponseEntity<?> startworkflow(@PathVariable String workflowId){
        String message = String.format(new NiceWorker().startWorkflow(workflowId));
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
        return ResponseEntity.ok().body(response);
    }
}
