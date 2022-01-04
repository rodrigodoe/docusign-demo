package com.example.demo.service;

import java.net.URI;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.auth.OAuth;
import com.example.demo.integration.AccountIntegration;
import com.example.demo.model.JWTGrantToken;
import com.example.demo.model.TokenResponse;
import com.example.demo.utils.DocuSignUtils;

import feign.FeignException.BadRequest;

@Service
public class TokenService {

	private static final String CODE = "code";

	private static final String CALLBACK = "http://localhost:8080/callback/";

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private ApiClient apiClient;

	@Autowired
	private TokenResponse token;

	@Value("${docusign.client.id}")
	private String integrationKey;

	@Autowired
	private AccountIntegration accountIngtegration;

	@Autowired
	private JWTGrantToken jwtGrandtToken;

	private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

	public void getAccesToken(String token) {

		TokenResponse response = null;

		try {
			response = accountIngtegration.getAccessToken("urn:ietf:params:oauth:grant-type:jwt-bearer", token);
			this.build(response);
			this.userInfoService.getUserInfo();

		} catch (BadRequest exception) {
			logger.info(exception.getMessage());

			apiClient.setOAuthBasePath(OAuth.DEMO_OAUTH_BASEPATH);

			URI authorizationUri = apiClient.getAuthorizationUri(integrationKey,
					Arrays.asList(jwtGrandtToken.getScope()), CALLBACK, CODE);

			logger.info("user neeeds to grant access =>> {}", authorizationUri);

		}

	}

	private void build(TokenResponse tokenResponse) {
		String bearerToken = DocuSignUtils.createBearerToken(tokenResponse.getAcessToken());
		token.setAcessToken(bearerToken);
		token.setRefreshToken(tokenResponse.getRefreshToken());
		token.setTokenType(tokenResponse.getTokenType());
		token.setExpiresIn(tokenResponse.getExpiresIn());

	}

}
