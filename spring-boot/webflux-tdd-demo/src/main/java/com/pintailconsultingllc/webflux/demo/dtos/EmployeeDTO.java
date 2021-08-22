package com.pintailconsultingllc.webflux.demo.dtos;

import com.pintailconsultingllc.webflux.demo.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    Integer id;
    String name;
    Integer salary;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.salary = employee.getSalary();
    }
}
