package com.pintailconsultingllc.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class WebFluxSecurityConfiguration {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(csrf -> csrf.disable());
//                .authorizeExchange()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin();
        return http.build();
    }
}
