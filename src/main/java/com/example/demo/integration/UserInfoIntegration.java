package com.example.demo.integration;

import javax.ws.rs.BadRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.api.UserInfoApi;
import com.example.demo.model.UserInfoResponse;

import feign.FeignException.BadRequest;
import feign.FeignException.Unauthorized;

@Service
public class UserInfoIntegration {

	@Autowired
	private UserInfoApi userInfoApi;

	private static final Logger logger = LoggerFactory.getLogger(EnvelopeIntegration.class);

	public UserInfoResponse getUserInfo(String bearerToken) {
		ResponseEntity<UserInfoResponse> response = null;

		try {
			response = userInfoApi.getUserInfo(bearerToken);

			if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
				throw new BadRequestException("BAD REQUEST");
			}

		} catch (BadRequest | Unauthorized exception) {
			logger.error(exception.getMessage());
		}

		return response == null ? null : response.getBody();

	}
}
