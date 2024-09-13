package com.example.unittestdemo.mappers;

//@Mapper(componentModel = "spring")
public interface EmployeeMapper<Employee, EmployeeDto> {

    EmployeeDto toDto(Employee employee);
}
