package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.TokenResponse;
import com.example.demo.model.UserInfoResponse;
import com.example.demo.service.DocusignService;

@RestController
public class DocuSignController {

	private static final Logger logger = LoggerFactory.getLogger(DocuSignController.class);

	@Autowired
	private UserInfoResponse userInfoResponse;

	@Autowired
	private TokenResponse tokenResponse;

	@Autowired
	private DocusignService docusignService;

	@GetMapping("/callback")
	public ResponseEntity<Object> callback() {

		return ResponseEntity.ok().build();
	}

	@GetMapping("/jwt")
	public ResponseEntity<Object> jwt() {

		logger.info("RESPONSE => {}", tokenResponse);

		logger.info("USER INFO => {}", userInfoResponse);

		if (tokenResponse.getAcessToken() != null) {
			docusignService.createEnvelope();
		}

		return ResponseEntity.ok().build();
	}

}
