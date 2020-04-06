package com.encryption.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encryption.challenge.dto.EncryptionChallengeDTO;
import com.encryption.challenge.dto.ResultEncryptionChallengeDTO;
import com.encryption.challenge.service.EncryptionChallengeService;

@RestController
public class EncryptionChallengeController {

	@Autowired
	private EncryptionChallengeService encryptionChallengeService;

	@GetMapping("/generate-data")
	public EncryptionChallengeDTO generateData(@RequestParam String token) throws Exception {
		return encryptionChallengeService.getDetailsEncryptionChallenge(token);
	}

	@PostMapping("/decrypt")
	public ResultEncryptionChallengeDTO decrypt(@RequestParam String token, @RequestBody EncryptionChallengeDTO encryptionChallengeDTO) throws Exception {
		return encryptionChallengeService.decryptMessage(token, encryptionChallengeDTO);
	}

}
