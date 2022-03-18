package com.pintailconsultingllc.demo.controllers;

import com.pintailconsultingllc.demo.dtos.RaceDTO;
import com.pintailconsultingllc.demo.entities.Race;
import com.pintailconsultingllc.demo.exceptions.ResourceLocationURIException;
import com.pintailconsultingllc.demo.services.RaceService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/races")
public class RaceController {

    private final RaceService raceService;

    @SneakyThrows
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> create(@RequestBody RaceDTO raceDTO) {
        final Race race = raceDTO.toEntity();
        return raceService.save(race)
                .map(saved -> ResponseEntity.created(createResourceUri(saved)).build());

    }

    private URI createResourceUri(Race race) {
        try {
            return new URI(String.format("/races/%s", race.getId()));
        } catch (URISyntaxException e) {
            throw new ResourceLocationURIException(e);
        }
    }
}
