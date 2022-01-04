package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.example.demo.model.TokenResponse;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

@FeignClient(name = "docuSignAccountTokenApi", url = "${docusign.account.endpoint:https://account-d.docusign.com}", configuration = AccountApi.MultipartSupportConfig.class)
public interface AccountApi {

	@PostMapping(value = "/oauth/token", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<TokenResponse> getAccessToken(@RequestPart(value = "grant_type") String grantType,
			@RequestPart(value = "assertion") String assertion);

	class MultipartSupportConfig {
		@Bean
		public Encoder feignFormEncoder() {
			return new SpringFormEncoder();
		}
	}

}
