package com.example.unittestdemo.services;

import com.example.unittestdemo.entities.Employee;
import com.example.unittestdemo.repositories.EmployeeRepository;
import com.example.unittestdemo.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setupTests() {
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void getEmployeeById_WithNotExistingId_ReturnEmptyOptional() {
        // 2. prepare mocked methods
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());

        // 3. call target method
        Optional<Employee> returnedEmployee = employeeService.getEmployeeById(1L);

        // 4. assert output
        assertFalse(returnedEmployee.isPresent(), "No employee was expected with id = 1");
    }

    @Test
    public void getEmployeeById_WithExistingId_ReturnRequestedEmployee() {
        // prepare input
        long existingId = 25L;
        Employee expectedEmployee = Employee.builder()
                .id(existingId).firstName("khaled").lastName("diab").email("khaled.diab@asset.com.eg")
                .build();

        // prepare mocked methods
        when(employeeRepository.findById(existingId)).thenReturn(Optional.of(expectedEmployee));

        // call target method
        Optional<Employee> returnedEmployee = employeeService.getEmployeeById(existingId);

        // assert output
        assertTrue(returnedEmployee.isPresent(), "Employee with id = " + existingId + " was expected");
        assertEquals(existingId, returnedEmployee.get().getId(), "Wrong employee is returned");
        assertTrue(returnedEmployee.get().getActive(), "Employee should be active");
    }

    @Test
    public void saveEmployee_UsingValidData_AddedSuccessfully() {
        // prepare input
        Employee input = Employee.builder()
                .firstName("tamer").lastName("shawky").email("tamer.shawky@asset.com.eg")
                .build();

        // prepare mocked methods
        when(employeeRepository.save(input)).thenAnswer((Answer<Employee>) invocation -> {
            Object[] args = invocation.getArguments();
            Employee dummyEmployee = (Employee) args[0];
            dummyEmployee.setId(5);
            return dummyEmployee;
        });

        // call target method
        Employee createdEmployee = employeeService.saveEmployee(input);

        // assert output
        assertNotEquals(0L, createdEmployee.getId(), "Id should be generated by JPA and cannot be 0");
        assertEquals(5L, createdEmployee.getId(), "Id should equal the used dummy Id");
        assertNotNull(createdEmployee.getEmail(), "Email was sent in input and shouldn't be null");
        assertEquals(input.getEmail(), createdEmployee.getEmail(), "Email should be the same as the one in input");
    }

    @Test
    public void saveEmployee_UsingExistingEmail_ThrowRuntimeException() {
        // prepare input
        Employee input = Employee.builder()
                .firstName("islam").lastName("gad").email("islam.gad@asset.com.eg")
                .build();

        // prepare mocked methods
        when(employeeRepository.findByEmail(input.getEmail())).thenReturn(Optional.of(input));

        // call target method
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> employeeService.saveEmployee(input),
                "Expected saveEmployee() to throw RuntimeException but it didn't"
        );


        // assert output
        assertEquals("Employee already exist with given email:islam.gad@asset.com.eg",
                thrown.getMessage(), "Not the expected error message");
    }
}
