package com.example.rqchallenge.service;

import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.employees.EmployeeCreationStatus;
import com.example.rqchallenge.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getEmployeesByNameSearch(String searchInput) {
        /*
         * This operation can be made dependent on DB
         * using a SQL query and get the result from DB itself.
         * */

        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList
                .stream()
                .filter(employee -> employee.getEmployeeName().matches("(?i).*" + searchInput + ".*"))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            return employeeRepository.findById(id);
        }

        String errorMessage = String.format("Employee not found for id : %d", id);

        LOGGER.error(errorMessage);
        throw new NoSuchElementException(errorMessage);
    }

    @Override
    public int getHighestSalaryOfEmployees() {
        /*
         * This operation can be made dependent on DB
         * using a SQL query and get the result from DB itself.
         * */

        List<Employee> employeeList = employeeRepository.findAll();

        return employeeList
                .stream()
                .max(Employee.employeeSalaryAcsendingComparator())
                .get()
                .getEmployeeSalary();
    }

    @Override
    public List<Employee> getTop10HighestEarningEmployeeNames() {
        /*
         * This operation can be made dependent on DB
         * using a SQL query and get the result from DB itself.
         * */

        List<Employee> employeeList = employeeRepository.findAll();

        return employeeList
                .stream()
                .sorted(Employee.employeeSalaryDescendingComparator())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeCreationStatus createEmployee(String name, int salary, int age) {
        Employee employee = prepareEmployeeData(name, salary, age);
        try {
            employeeRepository.save(employee);
            return EmployeeCreationStatus.SUCCESS;
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error(String.format("Employee creation failed for employee with details {Name : %s, Salary: %d, Age: %d}", name, salary, age));
            return EmployeeCreationStatus.FAILURE;
        }
    }

    @Override
    public String deleteEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isPresent()) {
            LOGGER.info(String.format("Deleting employee with id : %d", id));
            employeeRepository.delete(optionalEmployee.get());
            return optionalEmployee.get().getEmployeeName();
        }

        LOGGER.error(String.format("No any employee found for deletion with employee id : %d", id));
        throw new IllegalArgumentException(String.format("No any employee found for deletion with employee id : %d", id));
    }

    private Employee prepareEmployeeData(String name, int salary, int age) {
        Employee employee = new Employee();

        employee.setEmployeeName(name);
        employee.setEmployeeSalary(salary);
        employee.setEmployeeAge(age);

        return employee;
    }
}
