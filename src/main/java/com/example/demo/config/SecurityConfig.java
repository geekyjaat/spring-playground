package com.example.demo.config;

import com.example.demo.entities.Employee;
import com.example.demo.movie.Movie;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.repositories.IMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    EmployeeDetailsService employeeDetailsService;

    @Autowired
    IMovieRepository movieRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .mvcMatchers("/flights/**", "/math/**", "/lessons/**", "/movies/**", "/words/**", "/greeting")
                .permitAll();
        http.authorizeRequests().mvcMatchers("/admin/**").hasRole("MANAGER");
        http.httpBasic();
        http.authorizeRequests().anyRequest().authenticated();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(employeeDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile("default")
    public CommandLineRunner seedData(
            EmployeeRepository employeeRepository,
            IMovieRepository movieRepository
    ) {
        return (args) -> {
            employeeRepository.deleteAll();

            Employee employee = new Employee();
            employee.setName("Employee");
            employee.setSalary(24);
            employee.setUsername("employee");
            employee.setPassword(passwordEncoder().encode("my-employee-password"));
            employee.setRole("EMPLOYEE");
            employeeRepository.save(employee);

            Employee boss = new Employee();
            boss.setName("Bossy Boss");
            boss.setSalary(24);
            boss.setUsername("boss");
            boss.setPassword(passwordEncoder().encode("my-boss-password"));
            boss.setRole("MANAGER");
            employeeRepository.save(boss);

            Movie movie1 = new Movie("Boss Baby");
            movie1.setMovieId(1L);
            Movie movie2 = new Movie("Beauty and the Beast");
            movie2.setMovieId(2L);
            Movie movie3 = new Movie("Logan");
            movie3.setMovieId(3L);
            movieRepository.save(movie1);
            movieRepository.save(movie2);
            movieRepository.save(movie3);
        };

    }

}