package com.BnSoft.URL_Shortner_API.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "URL_SHORTNER_TABLE")
public class URL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long id;
	@Lob
	@Column(name = "Original_URL")
	private String originalURL;
	@Column(name = "Short_URL")
	private String shortURL;
	@Column(name = "Create_Date")
	private LocalDateTime createDate;
	@Column(name = "Expiration_Date")
	private LocalDateTime expirationDate;
}
