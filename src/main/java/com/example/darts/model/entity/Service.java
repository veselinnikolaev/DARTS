package com.example.darts.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "services")
@Data
public class Service extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @ManyToMany(mappedBy = "services")
    private List<Account> accounts;
}