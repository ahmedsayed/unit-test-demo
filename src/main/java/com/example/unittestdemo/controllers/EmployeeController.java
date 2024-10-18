package com.example.unittestdemo.controllers;

import com.example.unittestdemo.controllers.apis.EmployeeApi;
import com.example.unittestdemo.dtos.RequestDto;
import com.example.unittestdemo.entities.Employee;
import com.example.unittestdemo.services.EmployeeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
public class EmployeeController implements EmployeeApi {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee) {
        try {
            return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Employee> getAllEmployees(RequestDto requestDto) {
        log.info("requestDto => {}", requestDto.getSortDirection());
        return employeeService.getAllEmployees();
    }

    @Override
    public ResponseEntity<Optional<Employee>> getEmployeeById(long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(long id, Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployee(long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee deleted successfully!.", HttpStatus.OK);

    }

    @PostMapping("/status")
    public ResponseEntity<String> setStatus(@RequestBody RequestDto request) {
        // Process the status
        return ResponseEntity.ok("Received direction: " + request.getSortDirection());
    }
}
