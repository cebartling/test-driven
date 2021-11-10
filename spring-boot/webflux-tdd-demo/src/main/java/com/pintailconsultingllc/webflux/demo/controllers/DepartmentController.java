package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public Flux<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().map(DepartmentDTO::new);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<DepartmentDTO>> getDepartmentById(
            @PathVariable("id") int id
    ) {
        return departmentRepository.findById(id)
                .map(DepartmentDTO::new)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
