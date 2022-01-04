package com.example.demo.configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.example.demo.model.JWTGrantToken;
import com.example.demo.service.TokenService;

@Component
public class DocuSignTokenInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService restService;

	@Value("${docusign.client.id}")
	private String integrationKey;

	@Value("${docusign.api.username}")
	private String apiUserName;

	@Value("${docusign.url.path:account-d.docusign.com}")
	private String urlPath;

	@Autowired
	private JWTGrantToken jwtGrantToken;

	private static final String SCOPE = "scope";

	private static final Logger logger = LoggerFactory.getLogger(DocuSignTokenInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException, GeneralSecurityException {

		String token = JWT.create().withIssuer(jwtGrantToken.getIss()).withSubject(jwtGrantToken.getSubject())
				.withIssuedAt(jwtGrantToken.getIssuedAt()).withExpiresAt(jwtGrantToken.getExpiresAt())
				.withAudience(jwtGrantToken.getAud()).withClaim(SCOPE, jwtGrantToken.getScope())
				.sign(jwtGrantToken.getAlgorithm());

		logger.info("token =>> {}", token);

		restService.getAccesToken(token);

		return true;
	}

}
