package com.mbo.bootStrap;

import com.mbo.entities.Employee;
import com.mbo.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class LoadData implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public LoadData(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Mehdi")
                .lastName("BOUMZZI")
                .salary(4100)
                .job("JAVA Angular Developer")
                .sector("Computer Science")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Walid")
                .lastName("SAI")
                .salary(4500)
                .job(".NET React Developer")
                .sector("Computer Science")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Frederic")
                .lastName("TOO")
                .salary(4600)
                .job("Product Manager")
                .sector("Finance")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Berenice")
                .lastName("DOO")
                .salary(3600)
                .job("Product Manager")
                .sector("Gare & Connexion")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Cecile")
                .lastName("LOO")
                .salary(4000)
                .job("Product Manager")
                .sector("Gare & Connexion")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Nicolas")
                .lastName("FOO")
                .salary(3900)
                .job("Product Manager")
                .sector("Finance")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("AbdelMajid")
                .lastName("BOO")
                .salary(4200)
                .job("Manager")
                .sector("Gare & Connexion")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Adrien")
                .lastName("MOO")
                .salary(4000)
                .job("JAVA Angular Developer")
                .sector("Gare & Connexion")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Herve")
                .lastName("POO")
                .salary(3900)
                .job("PHP Developer")
                .sector("Gare & Connexion")
                .build());
        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName("Sylvie")
                .lastName("ROO")
                .salary(4300)
                .job("Product Manager")
                .sector("Gare & Connexion")
                .build());

        employeeRepository.saveAll(employees);
    }
}
