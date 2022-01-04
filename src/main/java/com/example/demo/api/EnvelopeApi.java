package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.model.EnvelopeDefinition;
import com.example.demo.model.EnvelopeResponse;

@FeignClient(name = "docusignEnvelopeApi", url = "${docusign.envelope.endpoint:https://demo.docusign.net/restapi/v2.1}")
public interface EnvelopeApi {

	@PostMapping(value = "/accounts/{accountId}/envelopes", produces = { "application/json" }, consumes = {
			"application/json" })
	ResponseEntity<EnvelopeResponse> createEnvelope(@RequestHeader("Authorization") String bearerToken,
			@PathVariable String accountId, @RequestBody EnvelopeDefinition definition);

}
