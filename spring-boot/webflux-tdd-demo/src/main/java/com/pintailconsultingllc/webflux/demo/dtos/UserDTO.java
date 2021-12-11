package com.pintailconsultingllc.webflux.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private int statusCode;
    private String status;
}
