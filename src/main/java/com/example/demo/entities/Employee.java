package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.CompactView.class)
    private Long id;
    @JsonView(Views.CompactView.class)
    private String name;
    @JsonView(Views.AllView.class)
    private int salary;
    @JsonView(Views.CompactView.class)
    private Long managerId;
}
