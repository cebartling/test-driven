package com.pintailconsultingllc.demo.repositories;

import com.pintailconsultingllc.demo.entities.Race;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RaceRepository extends ReactiveCrudRepository<Race, UUID> {

    @AllowFiltering
    List<Race> findByName(String name);

    List<Race> findByNameContaining(String name);
}
