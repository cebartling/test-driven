package com.pintailconsultingllc.testcontainers.demo.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;
}