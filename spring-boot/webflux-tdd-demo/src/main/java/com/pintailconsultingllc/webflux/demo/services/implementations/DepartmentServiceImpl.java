package com.pintailconsultingllc.webflux.demo.services.implementations;

import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.services.DepartmentService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public Mono<Department> create(DepartmentDTO departmentDTO) {
        return null;
    }
}
