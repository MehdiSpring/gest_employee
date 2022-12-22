package com.mbo.mappers;

import com.mbo.dto.EmployeeDto;
import com.mbo.entities.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDto employeeToEmployeeDto(Employee employee)
    {
        EmployeeDto employeeDto = new EmployeeDto();

        BeanUtils.copyProperties(employee,employeeDto);

        return employeeDto;
    }

    public Employee employeeDtoToEmployee(EmployeeDto employeeDto)
    {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDto,employee);

        return employee;
    }
}
