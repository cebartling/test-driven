package com.pintailconsultingllc.testcontainers.demo.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "stock_price_events")
public class StockPriceEvent {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private ZonedDateTime eventTimestamp;

    @Column(nullable = false)
    private Integer sharePriceInCents;

}