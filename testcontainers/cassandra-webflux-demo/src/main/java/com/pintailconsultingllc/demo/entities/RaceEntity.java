package com.pintailconsultingllc.demo.entities;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table("races")
public class RaceEntity {
    @PrimaryKey
    private UUID id;

    private String name;

    private String description;
}
