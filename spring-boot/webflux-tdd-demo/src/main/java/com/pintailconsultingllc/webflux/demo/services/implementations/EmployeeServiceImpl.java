package com.pintailconsultingllc.webflux.demo.services.implementations;

import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import com.pintailconsultingllc.webflux.demo.services.EmployeeService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Mono<Employee> create(EmployeeDTO employeeDTO) {
        return employeeRepository.save(new Employee(employeeDTO));
    }

    @Override
    public Mono<Employee> update(BigInteger id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id)
                .flatMap(employee -> {
                    employee.setName(employeeDTO.getName());
                    employee.setSalary(employeeDTO.getSalary());
                    return employeeRepository.save(employee);
                })
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Unable to find employee by ID: %d", id))));
    }

    @Override
    public Mono<Employee> delete(BigInteger id) {
        return employeeRepository.findById(id)
                .flatMap(employee -> {
                    employee.setDeleted(true);
                    return employeeRepository.save(employee);
                })
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Unable to find employee by ID: %d", id))));
    }
}
