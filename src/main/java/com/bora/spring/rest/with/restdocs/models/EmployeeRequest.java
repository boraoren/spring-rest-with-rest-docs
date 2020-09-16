package com.bora.spring.rest.with.restdocs.models;

public class EmployeeRequest {

    private String name;

    public EmployeeRequest(){}

    public EmployeeRequest(String withName){
        name = withName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
