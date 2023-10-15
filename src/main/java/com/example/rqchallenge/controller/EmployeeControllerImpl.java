package com.example.rqchallenge.controller;

import com.example.rqchallenge.employees.EmployeeCreationStatus;
import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class EmployeeControllerImpl implements IEmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws IOException {
        List<Employee> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        List<Employee> employees = employeeService.getEmployeesByNameSearch(searchString);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        Long employeeId = Long.parseLong(id);
        try {
            Optional<Employee> employee = employeeService.getEmployeeById(employeeId);
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } catch (NoSuchElementException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        Integer highestSalary = employeeService.getHighestSalaryOfEmployees();
        return new ResponseEntity<>(highestSalary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getTopTenHighestEarningEmployeeNames() {
        List<Employee> highestSalariedEmployees = employeeService.getTop10HighestEarningEmployeeNames();
        return new ResponseEntity<>(highestSalariedEmployees, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        try {
            String name = String.valueOf(employeeInput.get("name"));
            int age = Integer.parseInt(String.valueOf(employeeInput.get("age")));
            int salary = Integer.parseInt(String.valueOf(employeeInput.get("salary")));

            EmployeeCreationStatus employeeCreationStatus = employeeService.createEmployee(name, salary, age);

            if (employeeCreationStatus == EmployeeCreationStatus.SUCCESS) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (NumberFormatException numberFormatException) {
            LOGGER.error(String.format("Failed to parse input data to create employee: ", employeeInput));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        Long employeeId = Long.parseLong(id);
        String deletedEmployeeName;
        try {
            deletedEmployeeName = employeeService.deleteEmployee(employeeId);
            return new ResponseEntity<>(deletedEmployeeName, HttpStatus.OK);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
