package com.test.wf.math;

import com.netflix.conductor.client.worker.Worker;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskResult;

public class MultiplyBy2Worker implements Worker {

    private final String taskDefName;

    public MultiplyBy2Worker(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    @Override
    public String getTaskDefName() {
        return taskDefName;
    }

    @Override
    public TaskResult execute(Task task) {
        TaskResult result = new TaskResult(task);
        Integer addedValue = (Integer) task.getInputData().get("added");
        result.addOutputData("mb2", (addedValue) * 2);

        result.setStatus(TaskResult.Status.COMPLETED);
        return result;
    }
}
