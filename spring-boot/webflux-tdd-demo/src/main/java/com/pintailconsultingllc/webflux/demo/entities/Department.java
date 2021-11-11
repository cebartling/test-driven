package com.pintailconsultingllc.webflux.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    Integer id;
    String name;
    Boolean deleted;
}
