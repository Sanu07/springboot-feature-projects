package com.quarkus.repository;

import com.quarkus.entity.Employee;
import com.quarkus.model.EmployeeStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EmployeeRepository implements PanacheRepository<Employee> {

    public List<Employee> findByNameAndStatus(String name, EmployeeStatus status) {
        return find("name=:name and status=:status",
                Parameters.with("name", name).and("status", status)).list();
    }
}
