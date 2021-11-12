package com.pintailconsultingllc.webflux.demo.services;

import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

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
     * @param id An integer value representing the identifier.
     * @param employeeDTO An EmployeeDTO instance.
     * @return A Mono of type Employee instance.
     */
    Mono<Employee> update(BigInteger id, EmployeeDTO employeeDTO);

    /**
     * Soft delete an existing employee entity.
     *
     * @param id An integer value representing the identifier.
     * @return An Employee instance.
     */
    Mono<Employee> delete(BigInteger id);
}
