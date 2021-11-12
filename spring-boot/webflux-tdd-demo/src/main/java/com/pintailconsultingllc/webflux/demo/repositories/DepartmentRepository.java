package com.pintailconsultingllc.webflux.demo.repositories;

import com.pintailconsultingllc.webflux.demo.entities.Department;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DepartmentRepository extends ReactiveMongoRepository<Department, Integer> {
}
