package com.pintailconsultingllc.webflux.demo.services.implementations;

import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import com.pintailconsultingllc.webflux.demo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Mono<Employee> create(EmployeeDTO employeeDTO) {
        Employee employeeToBeSaved = new Employee(employeeDTO);
        return employeeRepository.save(employeeToBeSaved);
    }

    @Override
    public Mono<Employee> update(Integer id, EmployeeDTO employeeDTO) {
        return null;
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return null;
    }
}
