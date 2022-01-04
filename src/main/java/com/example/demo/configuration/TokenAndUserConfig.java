package com.example.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

import com.example.demo.model.TokenResponse;
import com.example.demo.model.UserInfoResponse;

@Configuration
public class TokenAndUserConfig {

	@RequestScope
	@Bean
	public TokenResponse token() {
		return new TokenResponse();
	}

	@RequestScope
	@Bean
	public UserInfoResponse user() {
		return new UserInfoResponse();
	}

}
