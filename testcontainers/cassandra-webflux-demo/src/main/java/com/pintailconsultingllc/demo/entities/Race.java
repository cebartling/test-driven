package com.pintailconsultingllc.demo.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Version;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@Table("races")
public class Race {
    @PrimaryKey
    private final UUID id;

    @Version
    private Long version;

    private final String name;

    private final String description;
}
