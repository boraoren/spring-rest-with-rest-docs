package com.bora.spring.rest.with.restdocs.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.bora.spring.rest.with.restdocs.models.Employee;
import com.bora.spring.rest.with.restdocs.models.EmployeeRequest;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class EmployeesApi {
    
    public final static Map<Long, Employee> employees = new HashMap<>();

    @PostMapping(value = "/v1/employees")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody Employee post(@RequestBody EmployeeRequest employeeRequest){
        long dummyId = 3L;
        String nameNew = employeeRequest.getName();
        
        final Employee employee = new Employee();
        employee.setId(dummyId);
        employee.setName(nameNew);
        employees.put(dummyId, employee);

        return employee;
    }

}
