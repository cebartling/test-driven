package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.exceptions.ResourceLocationURIException;
import com.pintailconsultingllc.webflux.demo.dtos.DepartmentDTO;
import com.pintailconsultingllc.webflux.demo.entities.Department;
import com.pintailconsultingllc.webflux.demo.repositories.DepartmentRepository;
import com.pintailconsultingllc.webflux.demo.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    public static final String APPLICATION_JSON = "application/json";
    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;

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

    @SneakyThrows
    @PostMapping(consumes = APPLICATION_JSON)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> create(@RequestBody DepartmentDTO departmentDTO) {
        return departmentService.create(departmentDTO)
                .map(department -> ResponseEntity.created(createResourceUri(department)).build());
    }

    private URI createResourceUri(Department department) {
        try {
            return new URI(String.format("/departments/%d", department.getId()));
        } catch (URISyntaxException e) {
            throw new ResourceLocationURIException(e);
        }
    }
}
