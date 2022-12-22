package com.mbo.services;

import com.mbo.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDto> findAll();
    List<EmployeeDto> findBySector(String sector);
    EmployeeDto findByLastName(String lastName);
    EmployeeDto addNewEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(String id, EmployeeDto employeeDto);
    void deleteEmployee(String id);
}
