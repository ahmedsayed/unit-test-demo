package com.example.unittestdemo.controllers;

import com.example.unittestdemo.entities.Employee;
import com.example.unittestdemo.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void saveEmployee_WithValidData_SavedSuccessfully() throws Exception {
        //
        Employee employee = Employee.builder()
                .firstName("John").lastName("Cena").email("john@gmail.com")
                .build();

        // precondition
        given(employeeService.saveEmployee(any(Employee.class))).willReturn(employee);

        // action
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // verify
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    public void saveEmployee_WithInValidData_ThrowsException() throws Exception {
        // prepare input
        Employee employee = Employee.builder()
                .firstName("mohamed").lastName("Cena")
                .build();

        when(employeeService.saveEmployee(employee)).thenThrow(new RuntimeException("error"));

        // action
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)));

        // verify
        response.andDo(print()).
                andExpect(status().is5xxServerError());
    }
}