package com.example.rqchallenge.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Comparator;


@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String employeeName;
    int employeeSalary;
    int employeeAge;
    String profileImage;

    public Long getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(int employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public int getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(int employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public static Comparator<Employee> employeeSalaryDescendingComparator(){
        return (e1, e2) -> Integer.compare(e2.getEmployeeSalary(), e1.getEmployeeSalary());
    }

    public static Comparator<Employee> employeeSalaryAcsendingComparator(){
        return Comparator.comparingInt(Employee::getEmployeeSalary);
    }
}
