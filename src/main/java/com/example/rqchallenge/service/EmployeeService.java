package com.example.rqchallenge.service;

import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.employees.EmployeeCreationStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getEmployeesByNameSearch(String searchInput);
    Optional<Employee> getEmployeeById(Long id);
    int getHighestSalaryOfEmployees();
    List<Employee> getTop10HighestEarningEmployeeNames();
    EmployeeCreationStatus createEmployee(String name, int salary, int age);
    String deleteEmployee(Long id);
}
