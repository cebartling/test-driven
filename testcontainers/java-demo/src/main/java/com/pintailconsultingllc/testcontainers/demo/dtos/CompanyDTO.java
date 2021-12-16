package com.pintailconsultingllc.testcontainers.demo.dtos;

import com.pintailconsultingllc.testcontainers.demo.entities.Company;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class
CompanyDTO {
    private UUID id;
    private String name;

    public static CompanyDTO from(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .build();
    }
}
