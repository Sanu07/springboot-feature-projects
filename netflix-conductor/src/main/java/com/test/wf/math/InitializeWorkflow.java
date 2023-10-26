package com.test.wf.math;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class InitializeWorkflow {

    public void initializeWorkflow() {
        TaskClient taskClient = new TaskClient();
        taskClient.setRootURI("http://192.168.1.2:8080/api/"); // Point this to the server API

        int threadCount = 3; // number of threads used to execute workers.  To avoid starvation, should be
        // same or more than number of workers

        Worker worker1 = new AddNumbersWorker("addnumbers");

        Worker worker2 = new MultiplyBy2Worker("multiplyby2");

        Worker worker3 = new MultiplyBy5Worker("multiplyby5");

        /*// Create TaskRunnerConfigurer
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, Collections.singletonList(worker1))
                        .withThreadCount(threadCount)
                        .build();*/

        Collection workerArrayList = new ArrayList<Worker>();
        workerArrayList.add(worker1);
        workerArrayList.add(worker2);
        workerArrayList.add(worker3);
        // Create TaskRunnerConfigurer
        TaskRunnerConfigurer configurer =
                new TaskRunnerConfigurer.Builder(taskClient, workerArrayList)
                        .withThreadCount(threadCount)
                        .build();
        // Start the polling and execution of tasks
        configurer.init();
    }
}
