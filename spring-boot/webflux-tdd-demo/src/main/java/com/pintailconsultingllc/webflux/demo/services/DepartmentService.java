package com.pintailconsultingllc.webflux.demo.services;

import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import reactor.core.publisher.Mono;

public interface DepartmentService {

    /**
     * Create a new department entity, using the department data transfer object data.
     *
     * @param departmentDTO A department data transfer object.
     * @return A Mono containing the newly created Department entity.
     */
    Mono<Department> create(DepartmentDTO departmentDTO);
}
