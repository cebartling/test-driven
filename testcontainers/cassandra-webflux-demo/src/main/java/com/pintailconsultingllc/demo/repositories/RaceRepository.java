package com.pintailconsultingllc.demo.repositories;

import com.pintailconsultingllc.demo.entities.RaceEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RaceRepository extends CassandraRepository<RaceEntity, UUID> {

    @AllowFiltering
    List<RaceEntity> findByName(String name);

    List<RaceEntity> findByNameContaining(String name);
}
