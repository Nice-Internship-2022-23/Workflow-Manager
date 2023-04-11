package com.awsswf.AWSFlow.controller;

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

    @PostMapping("/{workflowId}")
    public String startworkflow(@PathVariable String workflowId){
        return new NiceWorker().startWorkflow(workflowId);
    }
}
