package com.example.demo;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Profile("default")
    public CommandLineRunner seedData(EmployeeRepository employeeRepository) {
        return (args) -> {
            employeeRepository.deleteAll();
            Employee employee = new Employee();
            employee.setName("Employee");
            employee.setSalary(24);
            employee.setUsername("employee");
            employee.setPassword("my-employee-password");
            employee.setRole("EMPLOYEE");
            employeeRepository.save(employee);

            Employee boss = new Employee();
            boss.setName("Bossy Boss");
            boss.setSalary(24);
            boss.setUsername("boss");
            boss.setPassword("my-boss-password");
            boss.setRole("MANAGER");
            employeeRepository.save(boss);
        };

    }
}
