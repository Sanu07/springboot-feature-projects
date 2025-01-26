package com.influx.service;

import com.influx.dao.EmployeeDao;
import com.influx.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private MetricPublisher publisher;

    public void createEmployee(Employee employee) {
        employee.setStatus("created");
        Employee savedEmployee = employeeDao.save(employee);
        log.info("employee saved with id: {}", savedEmployee.getId());
        // publisher.publish(savedEmployee);
    }

    public void updateEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeDao.findById(id);
        employeeOptional.ifPresent(employee -> {
            employee.setStatus("updated");
            Employee savedEmployee = employeeDao.save(employee);
            log.info("employee updated with id: {}", savedEmployee.getId());
            publisher.publish(savedEmployee);
        });
    }
}
