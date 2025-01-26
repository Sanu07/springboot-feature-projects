package com.influx.controller;

import com.influx.model.Employee;
import com.influx.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employees")
@Slf4j
public class EmployeeResource {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public void updateEmployee(@PathVariable Long id) {
        employeeService.updateEmployee(id);
    }
}
