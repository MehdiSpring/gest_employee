package com.mbo.services;

import com.mbo.dto.EmployeeDto;
import com.mbo.entities.Employee;
import com.mbo.mappers.EmployeeMapper;
import com.mbo.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().map(employeeMapper::employeeToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDto> findBySector(String sector) {
        return employeeRepository.findBySector(sector).stream().map(employeeMapper::employeeToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto findByLastName(String lastName) {
        return employeeMapper.employeeToEmployeeDto(employeeRepository.findByLastName(lastName).
                orElseThrow(() -> new RuntimeException("Employee not found")));
    }

    @Override
    public EmployeeDto addNewEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.employeeDtoToEmployee(employeeDto);
        employee.setId(UUID.randomUUID().toString());
        Employee employeeSaved = employeeRepository.save(employee);
        return employeeMapper.employeeToEmployeeDto(employeeSaved);
    }

    @Override
    public EmployeeDto updateEmployee(String id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if(employeeDto.getFirstName() != null)
            employee.setFirstName(employeeDto.getFirstName());
        if(employeeDto.getLastName() != null)
            employee.setLastName(employeeDto.getLastName());
        if(employeeDto.getJob() != null)
            employee.setJob(employeeDto.getJob());
        if(employeeDto.getSector() != null)
            employee.setSector(employeeDto.getSector());

        return employeeMapper.employeeToEmployeeDto(employeeRepository.save(employee));
    }

    @Override
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}
