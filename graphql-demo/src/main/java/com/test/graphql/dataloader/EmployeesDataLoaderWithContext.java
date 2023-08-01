package com.test.graphql.dataloader;

import com.netflix.graphql.dgs.DgsDataLoader;
import com.test.graphql.generated.types.Employee;
import com.test.graphql.service.EmployeeService;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoaderWithContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

@DgsDataLoader(name = "employeesWithContext")
public class EmployeesDataLoaderWithContext implements MappedBatchLoaderWithContext<Integer, List<Employee>> {

    @Autowired
    Executor executor;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public CompletionStage<Map<Integer, List<Employee>>> load(Set<Integer> keys, BatchLoaderEnvironment environment) {
        return CompletableFuture.supplyAsync(() -> employeeService.getEmployees(keys), executor);
    }

}