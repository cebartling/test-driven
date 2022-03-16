package com.pintailconsultingllc.demo.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@Table("races")
public class Race {
    @PrimaryKey
    private final UUID id;

    @Version
    private Long version;

    private String name;

    private String description;
}
