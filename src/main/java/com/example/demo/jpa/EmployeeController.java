package com.example.demo.jpa;

import com.example.demo.entities.Employee;
import com.example.demo.entities.Views;
import com.example.demo.repositories.EmployeeRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/admin/employees")
    @JsonView(Views.AllView.class)
    public Iterable<Employee> getAdminEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees")
    @JsonView(Views.CompactView.class)
    public Iterable<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/me")
    public Employee getMe(@AuthenticationPrincipal Employee employee) {
        return employee;
    }
}
