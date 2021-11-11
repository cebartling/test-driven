package com.pintailconsultingllc.webflux.demo.services.implementations;

import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import com.pintailconsultingllc.webflux.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public Mono<Department> update(Integer id, DepartmentDTO departmentDTO) {
        return null;
    }

    @Override
    public Mono<Department> delete(Integer id) {
        return null;
    }
}
