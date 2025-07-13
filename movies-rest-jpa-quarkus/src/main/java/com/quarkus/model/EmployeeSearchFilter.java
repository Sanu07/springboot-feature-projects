package com.quarkus.model;

import lombok.Data;

@Data
public class EmployeeSearchFilter {
    private String name;
    private EmployeeStatus status;
}
