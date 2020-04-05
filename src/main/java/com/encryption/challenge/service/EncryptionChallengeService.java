package com.encryption.challenge.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.encryption.challenge.dto.EncryptionChallengeDTO;

@Service
public class EncryptionChallengeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionChallengeService.class);

	@Value("${generate.data.url}")
	private String GENERATE_DATA_URL;

	@Value("${token}")
	private String TOKEN;

	public EncryptionChallengeDTO getDetailsEncryptionChallenge() {
		return getGenerateData();
	}

	private EncryptionChallengeDTO getGenerateData() {
		final String url = GENERATE_DATA_URL + "?token=" + TOKEN;

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<EncryptionChallengeDTO> entity = new HttpEntity<EncryptionChallengeDTO>(headers);

		LOGGER.info("URL to request [" + url + "]");

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EncryptionChallengeDTO> response = restTemplate.exchange(url, HttpMethod.GET, entity, EncryptionChallengeDTO.class);

		if (response.getBody() != null) {
			return response.getBody();
		}
		return null;
	}
}
