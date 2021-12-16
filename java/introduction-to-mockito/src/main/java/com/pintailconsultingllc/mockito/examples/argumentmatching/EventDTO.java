package com.pintailconsultingllc.mockito.examples.argumentmatching;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDTO {
    private String eventType;
    private String eventData;
    private String eventTimestamp;
}
