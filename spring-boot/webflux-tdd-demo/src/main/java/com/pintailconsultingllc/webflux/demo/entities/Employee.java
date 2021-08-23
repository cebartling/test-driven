package com.pintailconsultingllc.webflux.demo.entities;

import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    Integer id;
    String name;
    Integer salary;
    boolean deleted;

    public Employee(EmployeeDTO employeeDTO) {
        this.id = employeeDTO.getId();
        this.name = employeeDTO.getName();
        this.salary = employeeDTO.getSalary();
    }
}
