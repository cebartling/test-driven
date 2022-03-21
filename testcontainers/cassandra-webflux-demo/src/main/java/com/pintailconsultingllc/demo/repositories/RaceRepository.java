package com.pintailconsultingllc.demo.repositories;

import com.pintailconsultingllc.demo.entities.Race;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RaceRepository extends ReactiveCassandraRepository<Race, UUID> {
}
