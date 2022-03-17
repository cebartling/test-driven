package com.pintailconsultingllc.demo.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RaceDTO {
    private final UUID id;
    private Long version;
    private String name;
    private String description;
}
