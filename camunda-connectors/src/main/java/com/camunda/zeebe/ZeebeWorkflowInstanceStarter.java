package com.camunda.zeebe;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.ClientStatusException;
import io.camunda.zeebe.client.api.command.CreateProcessInstanceCommandStep1;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.camunda.zeebe.client.api.worker.BackoffSupplier;
import io.camunda.zeebe.client.impl.worker.ExponentialBackoffBuilderImpl;
import io.grpc.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ZeebeWorkflowInstanceStarter {

    @Value("${workflow.version:0}")
    public int version;

    public static final Set<Status.Code> RETRIABLE_CODES = EnumSet.of(
            Status.Code.CANCELLED,
            Status.Code.DEADLINE_EXCEEDED,
            Status.Code.RESOURCE_EXHAUSTED,
            Status.Code.ABORTED,
            Status.Code.UNAVAILABLE,
            Status.Code.DATA_LOSS
    );

    private static BackoffSupplier backoffSupplier = new ExponentialBackoffBuilderImpl()
            .maxDelay(1000L)
            .minDelay(50L)
            .backoffFactor(1.5)
            .jitterFactor(0.2)
            .build();

    private static final String CORRELATION_KEY = "correlationKey";
    private static final String EVENT_TYPE_VARIABLE = "status";

    private static final int RETRY_COUNT = 3;

    @Autowired
    private ZeebeClient client;

    public String startProcessInstance(Map<String, Object> zeebeVariables, String processDefinitionKey) {
        if (zeebeVariables == null || zeebeVariables.isEmpty()) {
            log.error("Received empty zeebe variables");
            throw new RuntimeException("Received empty zeebe variables");
        }
        if (Objects.isNull(zeebeVariables.get(CORRELATION_KEY))) {
            log.error("Received empty zeebe correlation key");
            throw new RuntimeException("Received empty zeebe correlation key");
        }

        String correlationKey = zeebeVariables.get(CORRELATION_KEY).toString();
        String eventType = zeebeVariables.get(EVENT_TYPE_VARIABLE).toString();

        log.info("Starting zeebe process with processDefinitionKey: {}, correlationKey: {}, eventType: {}",
                processDefinitionKey, correlationKey, eventType);

        CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep3 command = null;
        if (version > 0) {
            command = client
                    .newCreateInstanceCommand()
                    .bpmnProcessId(processDefinitionKey)
                    .version(version)
                    .variables(zeebeVariables);
        } else {
            command = client
                    .newCreateInstanceCommand()
                    .bpmnProcessId(processDefinitionKey)
                    .latestVersion()
                    .variables(zeebeVariables);
        }
        ProcessInstanceEvent response = executeWithRetryOrFail(command, correlationKey, eventType);
        log.info("Started instance with processDefinitionKey: {}, bpmnProcessId: {}, version: {}, processInstanceKey: {}",
                response.getProcessDefinitionKey(), response.getBpmnProcessId(), response.getVersion(), response.getProcessInstanceKey());
        return String.valueOf(response.getProcessInstanceKey());
    }

    private ProcessInstanceEvent executeWithRetryOrFail(CreateProcessInstanceCommandStep1.CreateProcessInstanceCommandStep3 command, String correlationKey, String eventType) {
        long currentRetryDelay = 100L;
        int retryCount = RETRY_COUNT;

        while (retryCount > 0) {
            try {
                return command.send().join();
            } catch (Throwable ex) {
                if (ex instanceof ClientStatusException) {
                    final ClientStatusException statusException = (ClientStatusException) ex;
                    final Status.Code code = statusException.getStatus().getCode();
                    if (RETRIABLE_CODES.contains(code)) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(currentRetryDelay);
                        } catch (InterruptedException e) {
                            log.warn("Error while adding thread sleep");
                        }
                        log.warn("ERROR occured with RETRIABLE CODE");
                        retryCount--;
                    } else {
                        log.error("Error occurred while starting zeebe");
                        throw new RuntimeException("Error occurred while starting zeebe");
                    }
                } else {
                    log.error("Error occurred while starting zeebe");
                    throw new RuntimeException("Error occurred while starting zeebe");
                }
            }
        }
        log.error("Error occurred while starting zeebe");
        throw new RuntimeException("Error occurred while starting zeebe");
    }
}
