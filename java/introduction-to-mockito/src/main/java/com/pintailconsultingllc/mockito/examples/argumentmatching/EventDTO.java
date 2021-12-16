package com.pintailconsultingllc.mockito.examples.argumentmatching;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
public class EventDTO {
    private String eventType;
    private String eventData;
    private ZonedDateTime eventTimestamp;
}
