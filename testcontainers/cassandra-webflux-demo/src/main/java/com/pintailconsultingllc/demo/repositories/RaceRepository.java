package com.pintailconsultingllc.demo.repositories;

import com.pintailconsultingllc.demo.entities.RaceEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.List;
import java.util.UUID;

public interface RaceRepository extends CassandraRepository<RaceEntity, UUID> {

    @AllowFiltering
    List<RaceEntity> findByName(String name);

    List<RaceEntity> findByNameContaining(String name);
}
