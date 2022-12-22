package com.mbo.repositories;

import com.mbo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    List<Employee> findBySector(String sector);
    Optional<Employee> findByLastName(String lastName);
}
