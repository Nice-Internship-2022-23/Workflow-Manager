package com.awsswf.AWSFlow.service;

import java.util.Map;

public interface workflowManagerService {
    
    public Map<String, String> startWorkflow(String workflowId);
}
