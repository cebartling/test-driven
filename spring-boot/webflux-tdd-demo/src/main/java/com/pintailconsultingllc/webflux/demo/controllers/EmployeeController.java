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
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {

    public static final Duration TIMEOUT_DURATION = Duration.of(5000, ChronoUnit.MILLIS);
    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Flux<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll().map(EmployeeDTO::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<EmployeeDTO>> getEmployeeById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(employeeRepository.findById(id).map(EmployeeDTO::new));
    }

    @SneakyThrows
    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Mono<Void>> create(@RequestBody EmployeeDTO employeeDTO) {
        Mono<Employee> employeeMono = employeeService.create(employeeDTO);
        final URI uri = new URI(String.format("/employees/%d",
                Objects.requireNonNull(employeeMono.block(TIMEOUT_DURATION)).getId()));
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Mono<Void>> update(@PathVariable("id") Integer id,
                                       @RequestBody EmployeeDTO employeeDTO) {
        Mono<Employee> employeeMono = employeeService.update(id, employeeDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Mono<Void>> delete(@PathVariable("id") Integer id) {
        Mono<Void> voidMono = employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
