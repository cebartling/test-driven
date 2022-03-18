package com.pintailconsultingllc.demo.dtos;

import com.pintailconsultingllc.demo.entities.Race;
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

    public Race toEntity() {
        return Race.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .version(this.version)
                .build();
    }
}
