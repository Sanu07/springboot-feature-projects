package com.test.wf.math;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private WorkflowService workflowService;

    @PostMapping("start")
    public Object start(@RequestBody InputNumbers inputNumbers) {
        workflowService.startWorkflow(inputNumbers);
        return null;
    }
}
