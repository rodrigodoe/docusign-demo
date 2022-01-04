package com.example.demo.integration;

import javax.ws.rs.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.api.AccountApi;
import com.example.demo.model.TokenResponse;

import feign.FeignException.BadRequest;
import feign.FeignException.Unauthorized;

@Service
public class AccountIntegration {

	@Autowired
	private AccountApi accountApi;

	private static final Logger logger = LoggerFactory.getLogger(AccountIntegration.class);

	public TokenResponse getAccessToken(String grantType, String assertion) {
		ResponseEntity<TokenResponse> response = null;

		try {
			response = accountApi.getAccessToken(grantType, assertion);

			if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
				throw new BadRequestException("BAD REQUEST");
			}

		} catch (BadRequest | Unauthorized exception) {
			logger.error(exception.getMessage());
		}

		return response == null ? null : response.getBody();

	}

}
