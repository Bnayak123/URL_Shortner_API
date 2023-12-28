package com.BnSoft.URL_Shortner_API.Service;

import com.BnSoft.URL_Shortner_API.Dto.URL_DTO;
import com.BnSoft.URL_Shortner_API.Entitys.URL;

public interface URL_Service {
	URL generateShortLink(URL_DTO url_DTO);

	URL persistShortLink(URL url);

	URL getEncoderUrl(String url);

	void deleteShortLink(URL url);

}
