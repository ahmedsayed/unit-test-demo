package com.example.unittestdemo.controllers.apis;

import com.example.unittestdemo.entities.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/employees")
public interface EmployeeApi {

    @GetMapping
    List<Employee> getAllEmployees();

    @GetMapping("{id}")
    ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable("id") long id);

    @PostMapping
    ResponseEntity<Employee> createEmployee(@RequestBody Employee employee);

    @PutMapping("{id}")
    ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee);

    @DeleteMapping("{id}")
    ResponseEntity<String> deleteEmployee(@PathVariable("id") long id);
}
