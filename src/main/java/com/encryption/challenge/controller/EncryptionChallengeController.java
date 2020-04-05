package com.encryption.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encryption.challenge.dto.EncryptionChallengeDTO;
import com.encryption.challenge.service.EncryptionChallengeService;

@RestController
public class EncryptionChallengeController {

	@Autowired
	private EncryptionChallengeService encryptionChallengeService;

	@GetMapping(value = "/generate-data")
	public EncryptionChallengeDTO generateData() {
		return encryptionChallengeService.getDetailsEncryptionChallenge();
	}

}
