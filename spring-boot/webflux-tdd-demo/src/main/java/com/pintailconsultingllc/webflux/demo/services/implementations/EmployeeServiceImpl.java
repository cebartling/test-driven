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
        return employeeRepository.save(new Employee(employeeDTO));
    }

    @Override
    public Mono<Employee> update(Integer id, EmployeeDTO employeeDTO) {
        return employeeRepository.findById(id)
                .flatMap(employee -> {
                    employee.setName(employeeDTO.getName());
                    employee.setSalary(employeeDTO.getSalary());
                    return employeeRepository.save(employee);
                });
    }

    @Override
    public Mono<Employee> delete(Integer id) {
        return employeeRepository.findById(id)
                .flatMap(employee -> {
                    employee.setDeleted(true);
                    return employeeRepository.save(employee);
                });
    }
}
