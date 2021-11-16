package com.pintailconsultingllc.webflux.demo.repositories;

import com.pintailconsultingllc.webflux.demo.entities.Department;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.math.BigInteger;

public interface DepartmentRepository extends ReactiveMongoRepository<Department, BigInteger> {
}
