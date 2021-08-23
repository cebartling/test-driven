package com.pintailconsultingllc.webflux.demo.services;

import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import reactor.core.publisher.Mono;

public interface EmployeeService {

    /**
     * Create a new employee entity.
     *
     * @param employeeDTO An EmployeeDTO instance.
     * @return A Mono of type Employee instance.
     */
    Mono<Employee> create(EmployeeDTO employeeDTO);

    /**
     * Update an existing employee entity.
     *
     *
     * @param id
     * @param employeeDTO An EmployeeDTO instance.
     * @return A Mono of type Employee instance.
     */
    Mono<Employee> update(Integer id, EmployeeDTO employeeDTO);

    /**
     * Soft delete an existing employee entity.
     *
     *
     * @param id
     * @return An Employee instance.
     */
    Mono<Employee> delete(Integer id);
}
