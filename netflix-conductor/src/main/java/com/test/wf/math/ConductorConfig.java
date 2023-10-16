package com.test.wf.math;

import com.netflix.conductor.client.http.WorkflowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConductorConfig {

    @Value("${conductor.server.url}")
    private String conductorServerUrl;

    @Bean
    public WorkflowClient workflowClient() {
        WorkflowClient workflowClient = new WorkflowClient();
        workflowClient.setRootURI(conductorServerUrl);
        return workflowClient;
    }
}