package com.pintailconsultingllc.webflux.demo.repositories;

import com.pintailconsultingllc.webflux.demo.entities.Employee;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.math.BigInteger;

public interface EmployeeRepository extends ReactiveMongoRepository<Employee, BigInteger> {
}
