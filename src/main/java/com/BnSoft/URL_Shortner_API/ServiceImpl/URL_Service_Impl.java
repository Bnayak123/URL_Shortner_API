package com.BnSoft.URL_Shortner_API.ServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BnSoft.URL_Shortner_API.Dto.URL_DTO;
import com.BnSoft.URL_Shortner_API.Entitys.URL;
import com.BnSoft.URL_Shortner_API.Repositry.URL_Repositry;
import com.BnSoft.URL_Shortner_API.Service.URL_Service;
import com.google.common.hash.Hashing;

@Service
public class URL_Service_Impl implements URL_Service {
	@Autowired
	private URL_Repositry repositry;

	@Override
	public URL generateShortLink(URL_DTO url_DTO) {
		if (url_DTO.getUrl() != null && !url_DTO.getUrl().isBlank()) {
			String encodeUrl = encodeUrl(url_DTO.getUrl());
			URL urlpersist = new URL();
			urlpersist.setCreateDate(LocalDateTime.now());
			urlpersist.setOriginalURL(url_DTO.getUrl());
			urlpersist.setShortURL(encodeUrl);
			urlpersist.setExpirationDate(
					generateExpirationDate(url_DTO.getExpirationDate(), urlpersist.getCreateDate()));
			URL persistShortLink = persistShortLink(urlpersist);
			if (persistShortLink != null) {
				return persistShortLink;
			} else {
				return null;
			}
		}
		return null;
	}

	private LocalDateTime generateExpirationDate(String expirationDate, LocalDateTime create_Date) {
		if (expirationDate != null && !expirationDate.isBlank()) {
			return LocalDateTime.parse(expirationDate);
		}
		return create_Date.plusSeconds(120);
	}

	private String encodeUrl(String url) {

		LocalDateTime time = LocalDateTime.now();

		return Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
	}

	@Override
	public URL persistShortLink(URL url) {
		return repositry.save(url);
	}

	@Override
	public URL getEncoderUrl(String url) {
		return repositry.findByShortURL(url);
	}

	@Override
	public void deleteShortLink(URL url) {
		repositry.delete(url);
	}

}
