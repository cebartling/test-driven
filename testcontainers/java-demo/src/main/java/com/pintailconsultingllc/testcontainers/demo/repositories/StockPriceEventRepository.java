package com.pintailconsultingllc.testcontainers.demo.repositories;

import com.pintailconsultingllc.testcontainers.demo.entities.StockPriceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface StockPriceEventRepository extends JpaRepository<StockPriceEvent, UUID>, JpaSpecificationExecutor<StockPriceEvent> {
}