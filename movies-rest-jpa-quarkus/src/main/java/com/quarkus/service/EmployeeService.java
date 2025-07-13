package com.quarkus.service;

import com.quarkus.entity.Employee;
import com.quarkus.model.EmployeeSearchFilter;
import com.quarkus.repository.EmployeeRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;

@ApplicationScoped
@Named("employeeService")
public class EmployeeService {

    @Inject
    private EmployeeRepository employeeRepository;

    public Employee findById(Long id) {
        return Employee.findById(id, LockModeType.NONE);
    }

    public List<Employee> find(String name, int page) {
        return Employee.find("name=?1", Sort.ascending("name"), name)
                .page(page, 20)
                .list();
    }


    public List<Employee> findByNameAndStatus(@RequestBody EmployeeSearchFilter filter) {
        return employeeRepository.findByNameAndStatus(filter.getName(), filter.getStatus());
    }
}
