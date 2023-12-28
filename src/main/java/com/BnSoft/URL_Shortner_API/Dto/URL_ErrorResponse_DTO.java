package com.BnSoft.URL_Shortner_API.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class URL_ErrorResponse_DTO
{
    private String status;
    private String error;
}
