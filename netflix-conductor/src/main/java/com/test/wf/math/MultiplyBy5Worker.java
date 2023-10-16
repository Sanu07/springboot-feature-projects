package com.test.wf.math;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

public class MultiplyBy5Worker implements Worker {

    private final String taskDefName;

    public MultiplyBy5Worker(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        Integer doubledValue = (Integer) task.getInputData().get("doubled");

        result.addOutputData("mb5", doubledValue * 5);
        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }
}