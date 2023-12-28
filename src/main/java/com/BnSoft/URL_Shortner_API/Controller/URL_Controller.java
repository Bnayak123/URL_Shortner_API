package com.BnSoft.URL_Shortner_API.Controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.BnSoft.URL_Shortner_API.Dto.URL_DTO;
import com.BnSoft.URL_Shortner_API.Dto.URL_ErrorResponse_DTO;
import com.BnSoft.URL_Shortner_API.Dto.URL_Responce_DTO;
import com.BnSoft.URL_Shortner_API.Entitys.URL;
import com.BnSoft.URL_Shortner_API.Service.URL_Service;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class URL_Controller {

	@Autowired
	private URL_Service service;

	@PostMapping("/generate")
	public ResponseEntity<?> generateShortLink(@RequestBody URL_DTO url_DTO) {
		URL generateShortLink = service.generateShortLink(url_DTO);
		if (generateShortLink != null) {
			URL_Responce_DTO responce_DTO = new URL_Responce_DTO();
			responce_DTO.setOriginal_URL(generateShortLink.getOriginalURL());
			responce_DTO.setExpiration_Date(generateShortLink.getExpirationDate());
			responce_DTO.setShort_URL(generateShortLink.getShortURL());
			return new ResponseEntity<URL_Responce_DTO>(responce_DTO, HttpStatus.OK);
		} else {
			URL_ErrorResponse_DTO urlErrorResponseDto = new URL_ErrorResponse_DTO();
			urlErrorResponseDto.setStatus("404");
			urlErrorResponseDto.setError("There was an error processing your request. please try again.");
			return new ResponseEntity<URL_ErrorResponse_DTO>(urlErrorResponseDto, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{shortLink}")
	public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortLink, HttpServletResponse response)
			throws IOException {

		if (shortLink == null) {
			URL_ErrorResponse_DTO urlErrorResponseDto = new URL_ErrorResponse_DTO();
			urlErrorResponseDto.setError("Invalid Url");
			urlErrorResponseDto.setStatus("400");
			return new ResponseEntity<URL_ErrorResponse_DTO>(urlErrorResponseDto, HttpStatus.BAD_REQUEST);
		}
		URL urlToRet = service.getEncoderUrl(shortLink);

		if (urlToRet == null) {
			URL_ErrorResponse_DTO urlErrorResponseDto = new URL_ErrorResponse_DTO();
			urlErrorResponseDto.setError("Url does not exist or it might have expired!");
			urlErrorResponseDto.setStatus("400");
			return new ResponseEntity<URL_ErrorResponse_DTO>(urlErrorResponseDto, HttpStatus.BAD_REQUEST);
		}

		if (urlToRet.getExpirationDate().isBefore(LocalDateTime.now())) {
			service.deleteShortLink(urlToRet);
			URL_ErrorResponse_DTO urlErrorResponseDto = new URL_ErrorResponse_DTO();
			urlErrorResponseDto.setError("Url Expired. Please try generating a fresh one.");
			urlErrorResponseDto.setStatus("200");
			return new ResponseEntity<URL_ErrorResponse_DTO>(urlErrorResponseDto, HttpStatus.OK);
		}

		response.sendRedirect(urlToRet.getOriginalURL());
		return null;
	}

}
