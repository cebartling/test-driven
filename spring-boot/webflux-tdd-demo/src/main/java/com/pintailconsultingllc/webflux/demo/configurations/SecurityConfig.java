package com.pintailconsultingllc.webflux.demo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.POST, "/employees/update").hasRole("ADMIN")
//                .pathMatchers("/**").permitAll()
//                .and()
//                .httpBasic();
        return http.build();
    }
}
