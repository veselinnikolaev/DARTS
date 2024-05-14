package com.example.darts.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "accounts")
@Data
@SuperBuilder
@NoArgsConstructor
public class Account extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, unique = true)
    @Email
    private String email;
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String password;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String about;
    @ManyToMany(mappedBy = "accounts")
    private List<Service> services;
    @ManyToMany(mappedBy = "accounts")
    private List<Skill> skills;
    @ManyToMany(mappedBy = "accounts")
    private List<Company> companies;
}
