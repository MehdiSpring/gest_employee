package com.mbo.web;

import com.mbo.dto.EmployeeDto;
import com.mbo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findAll()
    {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/bySector")
    public ResponseEntity<List<EmployeeDto>> findBySector(@RequestParam String sector)
    {
        return new ResponseEntity<>(employeeService.findBySector(sector), HttpStatus.OK);
    }

    @GetMapping("/byLastName")
    public ResponseEntity<EmployeeDto> findByLastName(@RequestParam String lastName)
    {
        return new ResponseEntity<>(employeeService.findByLastName(lastName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> addNewEmployee(@RequestBody @Validated EmployeeDto employeeDto)
    {
        return new ResponseEntity<>(employeeService.addNewEmployee(employeeDto), HttpStatus.CREATED);
    }

    @PatchMapping("{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable String id, @RequestBody @Validated EmployeeDto employeeDto)
    {
        return new ResponseEntity<>(employeeService.updateEmployee(id, employeeDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id)
    {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.GONE);
    }
}
