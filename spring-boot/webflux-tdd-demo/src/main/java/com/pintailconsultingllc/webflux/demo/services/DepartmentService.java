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

    /**
     * Update an existing department entity, using the department data transfer object data.
     *
     * @param id The unique identifier for the department.
     * @param departmentDTO A department data transfer object.
     * @return A Mono containing the updated Department entity.
     */
    Mono<Department> update(Integer id, DepartmentDTO departmentDTO);

    /**
     * Soft-delete an existing department entity, using the id.
     *
     * @param id The unique identifier for the department.
     * @return A Mono containing the soft-deleted Department entity.
     */
    Mono<Department> delete(Integer id);
}
