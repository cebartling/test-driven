package com.pintailconsultingllc.webflux.demo.repositories;

import com.pintailconsultingllc.webflux.demo.entities.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface DepartmentRepository extends ReactiveCrudRepository<Department, Integer> {
}
