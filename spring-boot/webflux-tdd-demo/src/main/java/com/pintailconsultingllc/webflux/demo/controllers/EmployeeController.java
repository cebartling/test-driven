package com.pintailconsultingllc.webflux.demo.controllers;

import com.pintailconsultingllc.webflux.demo.dtos.EmployeeDTO;
import com.pintailconsultingllc.webflux.demo.entities.Employee;
import com.pintailconsultingllc.webflux.demo.repositories.EmployeeRepository;
import com.pintailconsultingllc.webflux.demo.services.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @GetMapping
    public Flux<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().map(EmployeeDTO::new);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<EmployeeDTO>> getEmployeeById(@PathVariable("id") Integer id) {
        return employeeRepository.findById(id)
                .map(EmployeeDTO::new)
                .map(employeeDTO -> ResponseEntity.ok().body(employeeDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @SneakyThrows
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> create(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.create(employeeDTO)
                .map(employee -> ResponseEntity.created(createResourceUri(employee)).build());
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> update(@PathVariable("id") Integer id,
                                             @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.update(id, employeeDTO)
                .map(employee -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Integer id) {
        return employeeService.delete(id)
                .map(employee -> ResponseEntity.noContent().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private URI createResourceUri(Employee employee) {
        try {
            return new URI(String.format("/employees/%d", employee.getId()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
