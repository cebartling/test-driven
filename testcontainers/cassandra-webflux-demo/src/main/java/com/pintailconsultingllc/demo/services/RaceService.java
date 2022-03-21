package com.pintailconsultingllc.demo.services;

import com.pintailconsultingllc.demo.entities.Race;
import com.pintailconsultingllc.demo.repositories.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Race services.
 */
@RequiredArgsConstructor
@Service
public class RaceService {

    private final RaceRepository raceRepository;

    public Mono<Race> save(Race race) {
        return raceRepository.save(race);
    }


}
