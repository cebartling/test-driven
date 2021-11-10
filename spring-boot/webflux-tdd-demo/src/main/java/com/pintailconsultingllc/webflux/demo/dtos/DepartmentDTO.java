package com.pintailconsultingllc.webflux.demo.dtos;

import com.pintailconsultingllc.webflux.demo.entities.Department;
import lombok.Data;

@Data
public class DepartmentDTO {
    Integer id;
    String name;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
    }
}
