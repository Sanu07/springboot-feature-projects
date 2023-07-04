package com.camunda.controller;

import com.camunda.entity.ImageTranscription;
import com.camunda.zeebe.ZeebeWorkflowInstanceStarter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
public class TranscriptionController {

    @Value("${workflow.name:image-transcription-workflow}")
    private String workflow;
    @Autowired
    private ZeebeWorkflowInstanceStarter zeebeWorkflowInstanceStarter;

    @PostMapping("start")
    public ResponseEntity<Void> startZeebeWorkflow(@RequestBody ImageTranscription imageTranscription) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("body", imageTranscription.getBody());
        variables.put("status", imageTranscription.getStatus());
        variables.put("hasImage", imageTranscription.getHasImage());
        variables.put("imageCount", imageTranscription.getImageCount());
        variables.put("correlationKey", imageTranscription.getCorrelationKey());

        String processInstanceKey = zeebeWorkflowInstanceStarter.startProcessInstance(variables, workflow);
        log.info("Started processInstance: {}", processInstanceKey);
        return ResponseEntity.ok().build();
    }

    @GetMapping("start/{workflow}")
    public ResponseEntity<Void> startAWorkflow(@PathVariable String workflow) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("body1", "test-body-1");
        variables.put("status", "TEST_STATUS");
        variables.put("correlationKey", String.valueOf(new Random().nextInt()));
        String processInstanceKey = zeebeWorkflowInstanceStarter.startProcessInstance(variables, workflow);
        log.info("Started processInstance: {}", processInstanceKey);
        return ResponseEntity.ok().build();
    }
}
