package com.BnSoft.URL_Shortner_API.Dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class URL_Responce_DTO {
	private String original_URL;
	private String short_URL;
	private LocalDateTime expiration_Date;

}
