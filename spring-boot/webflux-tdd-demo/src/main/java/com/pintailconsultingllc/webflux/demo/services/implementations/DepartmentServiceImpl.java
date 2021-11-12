package com.pintailconsultingllc.webflux.demo.services.implementations;

import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import com.pintailconsultingllc.webflux.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Mono<Department> create(DepartmentDTO departmentDTO) {
        final Department newDepartment = Department.builder()
                .name(departmentDTO.getName())
                .build();
        return departmentRepository.save(newDepartment);
    }

    @Override
    public Mono<Department> update(BigInteger id, DepartmentDTO departmentDTO) {
        return departmentRepository.findById(id)
                .flatMap(department -> {
                    department.setName(departmentDTO.getName());
                    return departmentRepository.save(department);
                })
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Unable to find department by ID: %d", id))));
    }

    @Override
    public Mono<Department> delete(BigInteger id) {
        return departmentRepository.findById(id)
                .flatMap(department -> {
                    department.setDeleted(true);
                    return departmentRepository.save(department);
                })
                .switchIfEmpty(Mono.error(new RuntimeException(String.format("Unable to find department by ID: %d", id))));
    }
}
