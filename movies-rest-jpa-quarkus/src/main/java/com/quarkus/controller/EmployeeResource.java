package com.quarkus.controller;

import com.quarkus.entity.Employee;
import com.quarkus.model.EmployeeSearchFilter;
import com.quarkus.service.EmployeeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.List;

@Path("employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    private EmployeeService employeeService;

    @Path("name/{name}")
    @GET
    public List<Employee> find(@PathParam("name") String name, @QueryParam("page") int page) {
        return employeeService.find(name, page);
    }

    @Path("{id}")
    @GET
    public Employee findById(@PathParam("id") Long id) {
        return employeeService.findById(id);
    }

    @POST
    public List<Employee> findByNameAndStatus(@RequestBody EmployeeSearchFilter filter) {
        return employeeService.findByNameAndStatus(filter);
    }
}
