package com.quarkus.entity;

import com.quarkus.model.EmployeeStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employee")
@Data
public class Employee extends PanacheEntity {

    private Long id;
    @Column(name = "joining_date")
    private OffsetDateTime joining;
    private String name;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;

}
