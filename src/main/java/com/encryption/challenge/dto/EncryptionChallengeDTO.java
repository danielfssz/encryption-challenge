package com.encryption.challenge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptionChallengeDTO {

	@JsonProperty(value = "numero_casas")
	private Long numeroCasas;

	@JsonProperty(value = "token")
	private String token;

	@JsonProperty(value = "cifrado")
	private String cifrado;

	@JsonProperty(value = "decifrado")
	private String decifrado;

	@JsonProperty(value = "resumo_criptografico")
	private String resumoCriptografico;

	public Long getNumeroCasas() {
		return numeroCasas;
	}

	public void setNumeroCasas(Long numeroCasas) {
		this.numeroCasas = numeroCasas;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCifrado() {
		return cifrado;
	}

	public void setCifrado(String cifrado) {
		this.cifrado = cifrado;
	}

	public String getDecifrado() {
		return decifrado;
	}

	public void setDecifrado(String decifrado) {
		this.decifrado = decifrado;
	}

	public String getResumoCriptografico() {
		return resumoCriptografico;
	}

	public void setResumoCriptografico(String resumoCriptografico) {
		this.resumoCriptografico = resumoCriptografico;
	}

	@Override
	public String toString() {
		return "EncryptionChallengeDTO [numeroCasas=" + numeroCasas + ", token=" + token + ", cifrado=" + cifrado
		    + ", decifrado=" + decifrado + ", resumoCriptografico=" + resumoCriptografico + "]";
	}

}
