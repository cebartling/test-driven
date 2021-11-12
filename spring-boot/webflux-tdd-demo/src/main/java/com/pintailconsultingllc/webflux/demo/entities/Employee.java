package com.pintailconsultingllc.webflux.demo.entities;

import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document(collection = "EMPLOYEES")
public class Employee {
    @Id
    BigInteger id;
    String name;
    Integer salary;
    boolean deleted;

    public Employee(EmployeeDTO employeeDTO) {
        this.id = employeeDTO.getId();
        this.name = employeeDTO.getName();
        this.salary = employeeDTO.getSalary();
    }
}
