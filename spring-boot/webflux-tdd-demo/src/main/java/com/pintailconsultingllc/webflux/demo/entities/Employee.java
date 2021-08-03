package com.pintailconsultingllc.webflux.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    Integer id;
    String name;
    Integer salary;
}
