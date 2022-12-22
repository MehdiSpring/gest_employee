package com.mbo.services;

import com.mbo.dto.EmployeeDto;
import com.mbo.entities.Employee;
import com.mbo.mappers.EmployeeMapper;
import com.mbo.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    public static final String MEHDI = "Mehdi";
    public static final String BOUMZZI = "BOUMZZI";

    @Mock
    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    EmployeeServiceImpl employeeService;

    List<Employee> employees = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employeeMapper = new EmployeeMapper();
        employeeService = new EmployeeServiceImpl(employeeRepository, employeeMapper);

        employees.add(new Employee().builder()
                .id(UUID.randomUUID().toString())
                .firstName(MEHDI)
                .lastName(BOUMZZI)
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
    }

    @Test
    void findAll() {
        //Given
        List<EmployeeDto> employeesDto;

        //When
        when(employeeRepository.findAll()).thenReturn(employees);

        //Then
        employeesDto = employeeService.findAll();
        assertEquals(3, employeesDto.size());
    }

    @Test
    void findBySector() {
        //Given
        List<EmployeeDto> employeesDto;

        //When
        when(employeeRepository.findBySector(anyString())).thenReturn(employees);

        //Then
        employeesDto = employeeService.findBySector("test");
        assertEquals(3, employeesDto.size());
    }

    @Test
    void findByLastName() {
        //Given
        Optional<Employee> employee = Optional.of(new Employee().builder()
                .firstName(MEHDI).lastName(BOUMZZI).build());

        //When
        when(employeeRepository.findByLastName(anyString())).thenReturn(employee);

        //Then
        EmployeeDto employeeDto = employeeService.findByLastName(BOUMZZI);
        assertEquals(MEHDI, employeeDto.getFirstName());
        assertEquals(BOUMZZI, employeeDto.getLastName());
    }

    @Test
    void addNewEmployee() {
        //Given
        Employee employeeSaved = new Employee().builder()
                .firstName(MEHDI).lastName(BOUMZZI).build();

        //When
        when(employeeRepository.save(any())).thenReturn(employeeSaved);
        
        //Then
        EmployeeDto employeeDto = employeeService.addNewEmployee(new EmployeeDto());
        assertEquals(MEHDI, employeeDto.getFirstName());
        assertEquals(BOUMZZI, employeeDto.getLastName());
    }

    @Test
    void updateEmployee() {
        //Given
        EmployeeDto employeeDtoParam = new EmployeeDto().builder().sector("GARE && Connexion").build();
        Optional<Employee> employeeFound = Optional.of(new Employee().builder().firstName("Frederic").
                                    lastName("TOO").sector("Finance").build());

        ArgumentCaptor<Employee> employeeCapture = ArgumentCaptor.forClass(Employee.class);

        //When
        when(employeeRepository.findById(anyString())).thenReturn(employeeFound);
        when(employeeRepository.save(any())).thenReturn(new Employee());

        //Then
        employeeService.updateEmployee("testId", employeeDtoParam);
        verify(employeeRepository, times(1)).save(employeeCapture.capture());
        Employee employeeUpdated = employeeCapture.getValue();
        assertEquals("Frederic", employeeUpdated.getFirstName());
        assertEquals("TOO", employeeUpdated.getLastName());
        assertEquals("GARE && Connexion", employeeUpdated.getSector());
    }

    @Test
    void deleteEmployee()
    {
        //Given
        String id = "idTodelete";

        //When, Then
        employeeService.deleteEmployee(id);
        verify(employeeRepository, times(1)).deleteById(anyString());
    }
}