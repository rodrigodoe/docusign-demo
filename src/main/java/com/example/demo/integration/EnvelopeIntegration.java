package com.example.demo.integration;

import javax.ws.rs.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.api.EnvelopeApi;
import com.example.demo.model.EnvelopeDefinition;
import com.example.demo.model.EnvelopeResponse;

import feign.FeignException.BadRequest;
import feign.FeignException.Unauthorized;

@Service
public class EnvelopeIntegration {
	
	@Autowired
	private EnvelopeApi envelopeApi;

	private static final Logger logger = LoggerFactory.getLogger(EnvelopeIntegration.class);

	public EnvelopeResponse createEnvelope(String bearerToken, String accountId, EnvelopeDefinition definition) {
		ResponseEntity<EnvelopeResponse> response = null;

		try {
			response = envelopeApi.createEnvelope(bearerToken, accountId, definition);

			if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
				throw new BadRequestException("BAD REQUEST");
			}

		} catch (BadRequest | Unauthorized exception) {
			logger.error(exception.getMessage());
		}

		return response == null ? null : response.getBody();

	}

}
