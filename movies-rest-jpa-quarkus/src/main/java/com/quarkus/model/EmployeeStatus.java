package com.quarkus.model;

import lombok.Getter;

@Getter
public enum EmployeeStatus {
    CREATED("created"),
    UPDATED("updated"),
    DELETED("deleted");

    private final String status;

    EmployeeStatus(String status) {
        this.status = status;
    }
}
