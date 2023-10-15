package com.example.rqchallenge;

import com.example.rqchallenge.entity.Employee;
import com.example.rqchallenge.service.EmployeeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RqChallengeApplicationTests {

    @Autowired
    EmployeeService employeeService;

    @BeforeAll
    void contextLoads() {
        int[] salaries = {50000, 22000, 31000, 57000, 56000,
                          71000, 67000, 77900, 12000, 43000,
                          65000, 45000, 21000};

        String[] employeeNames = {"John", "Mahendra", "Arjunsing", "Vikas", "Nidhi",
                                  "Pankhudi", "Sunidhi", "Sourabh", "Karansing", "Vicky",
                                  "Ritwik", "Ved", "Nitik"};

        int[] ages = {23, 24, 26, 27, 21,
                      22, 23, 32, 22, 21,
                      23, 24, 24};

        for(int counter = 0; counter <= 12; counter++){
            employeeService.createEmployee(employeeNames[counter], salaries[counter], ages[counter]);
        }
    }

    @Test
    @DisplayName("testGetAllEmployees")
    void testGetAllEmployees(){
        List<Employee> employees = employeeService.getAllEmployees();
        Assert.isTrue(employees.size() == 12, "Employee size is not as expected.");
    }

    @Test
    void testGetEmployeeById() {
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(5L);
        Employee employee = employeeOptional.get();

        int expectedSalary = 56000;
        String expectedName = "Nidhi";
        int expectedAge = 21;

        Assert.isTrue(employee.getEmployeeSalary() == expectedSalary, String.format("Employee salary is not as expected, Expected : %d, Actual : %d", expectedSalary, employee.getEmployeeSalary()));
        Assert.isTrue(employee.getEmployeeName() == expectedName, String.format("Employee name is not as expected, Expected : %s, Actual : %s", expectedName, employee.getEmployeeName()));
        Assert.isTrue(employee.getEmployeeAge() == expectedAge, String.format("Employee age is not as expected, Expected : %d, Actual : %d", expectedAge, employee.getEmployeeAge()));
    }


    @Test
    void testGetHighestSalaryOfAnEmployee(){
        int highestSalary = employeeService.getHighestSalaryOfEmployees();
        int expectedSalary = 77900;

        Assert.isTrue(highestSalary == expectedSalary, String.format("Invalid Highest Salary : %d, expected salary is: %d", highestSalary, expectedSalary));
    }

    @Test
    void testDeleteEmployee(){
        Long employeeIdToBeDeleted = 1L;
        String expectedDeletedName = "John";

        String actualDeletedName = employeeService.deleteEmployee(employeeIdToBeDeleted);

        Assert.isTrue(actualDeletedName.equals(expectedDeletedName), String.format("Deletion test failed, expected name of deleted employee : %s, actual name : %s", expectedDeletedName, actualDeletedName));
    }
}
