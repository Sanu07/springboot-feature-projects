package com.test.graphql.service;

import com.test.graphql.generated.types.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class EmployeeService {

    public Map<Integer, List<Employee>> getEmployees(Set<Integer> set) {
        final Map<Integer, List<Employee>> test1 = Map.of(
                11, Arrays.asList(Employee.newBuilder().id(11).name("test1").build())
        );
        log.info(test1.toString());
        return test1;
    }
}
