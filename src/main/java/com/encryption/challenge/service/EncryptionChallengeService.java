package com.encryption.challenge.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.encryption.challenge.dto.EncryptionChallengeDTO;
import com.encryption.challenge.dto.ResultEncryptionChallengeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EncryptionChallengeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionChallengeService.class);

	@Value("${generate.data.url}")
	private String GENERATE_DATA_URL;

	@Value("${submit.solution.url}")
	private String SUBMIT_SOLUTION_URL;

	public EncryptionChallengeDTO getDetailsEncryptionChallenge(String token) throws Exception {
		validateToken(token);

		return getGenerateData(token);
	}

	private EncryptionChallengeDTO getGenerateData(String token) {
		final String url = GENERATE_DATA_URL + "?token=" + token;

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

	public ResultEncryptionChallengeDTO decryptMessage(String token, EncryptionChallengeDTO encryptionChallengeDTO) throws Exception {
		validateToken(token);

		char[] messageSplit = encryptionChallengeDTO.getCifrado().toCharArray();

		StringBuilder sbMessageDecrypt = new StringBuilder();

		for (char letter : messageSplit) {
			int positionInAlphabet = findCharAtAlphabet(letter);

			if (positionInAlphabet < 0) {
				sbMessageDecrypt.append(letter);
				continue;
			}

			int positionNewValue = positionInAlphabet - encryptionChallengeDTO.getNumeroCasas().intValue();

			if (positionNewValue < 1) {
				positionNewValue = positionNewValue + 26;
			}

			char letterNewValue = (char) (positionNewValue + 97 - 1);

			sbMessageDecrypt.append(letterNewValue);
		}

		encryptionChallengeDTO.setDecifrado(sbMessageDecrypt.toString());

		encryptionChallengeDTO.setResumoCriptografico(DigestUtils.sha1Hex(encryptionChallengeDTO.getDecifrado()));

		return sendDecryptionData(token, encryptionChallengeDTO);
	}

	private ResultEncryptionChallengeDTO sendDecryptionData(String token, EncryptionChallengeDTO encryptionChallengeDTO) throws IOException {
		final String url = SUBMIT_SOLUTION_URL + "?token=" + token;

		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(encryptionChallengeDTO);

		Path path = Paths.get("answer.json");
		byte[] strToBytes = json.getBytes();

		Files.write(path, strToBytes);

		LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("answer", new FileSystemResource("answer.json"));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		RestTemplate template = new RestTemplate();

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(map, headers);
		ResponseEntity<ResultEncryptionChallengeDTO> result = template.exchange(url, HttpMethod.POST, requestEntity, ResultEncryptionChallengeDTO.class);

		return result.getBody();
	}

	public int findCharAtAlphabet(char letter) {
		int asciiNumber = (int) letter;
		final int offset = 97 - 1; // 97 is ASCII code for 'a', -1 since ASCII table starts at 0

		int numberInAlphabet = asciiNumber - offset;

		return numberInAlphabet;
	}

	private void validateToken(String token) throws Exception {
		if (StringUtils.isEmpty(token)) {
			throw new Exception("Token is null");
		}
	}

}
