package com.pintailconsultingllc.webflux.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceInformationDTO {
    private String employeeId;
    private Integer federalIncomeTaxesYearToDateInCents;
    private Integer stateIncomeTaxesYearToDateInCents;
}
