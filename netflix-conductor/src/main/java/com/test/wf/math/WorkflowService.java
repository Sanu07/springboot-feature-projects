package com.test.wf.math;

import com.netflix.conductor.client.http.WorkflowClient;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WorkflowService {

    @Autowired
    private WorkflowClient workflowClient;

    public Map<String, Object> startWorkflow(InputNumbers inputNumbers) {
        StartWorkflowRequest request = new StartWorkflowRequest();
        request.setName("math_workflow");
        Map<String, Object> inputData = new HashMap<>();
        inputData.put("num1", inputNumbers.getNum1());
        inputData.put("num2", inputNumbers.getNum2());
        request.setInput(inputData);

        String workflowId = workflowClient.startWorkflow(request);
        log.info("Workflow id: {}", workflowId);

        return Map.of("workflowId", workflowId);
    }
}
