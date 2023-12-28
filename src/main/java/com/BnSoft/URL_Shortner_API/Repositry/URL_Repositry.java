package com.BnSoft.URL_Shortner_API.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BnSoft.URL_Shortner_API.Entitys.URL;

@Repository
public interface URL_Repositry extends JpaRepository<URL, Long> {
	URL findByShortURL(String short_Link);

}
