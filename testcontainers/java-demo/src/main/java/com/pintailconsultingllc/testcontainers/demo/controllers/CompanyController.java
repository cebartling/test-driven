package com.pintailconsultingllc.testcontainers.demo.controllers;

import com.pintailconsultingllc.testcontainers.demo.dtos.CompanyDTO;
import com.pintailconsultingllc.testcontainers.demo.repositories.CompanyRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping( "/api/companies")
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompanyDTO>> findAll() {
        final List<CompanyDTO> dtoList = companyRepository.findAll()
                .stream()
                .map(CompanyDTO::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}
