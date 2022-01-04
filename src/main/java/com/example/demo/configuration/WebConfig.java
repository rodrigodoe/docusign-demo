package com.example.demo.configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.auth0.jwt.algorithms.Algorithm;
import com.docusign.esign.client.ApiClient;
import com.example.demo.model.JWTGrantToken;
import com.example.demo.utils.DocuSignUtils;

import feign.Logger;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final String SIGNATURE_IMPERSONATION = "signature impersonation";

	private static final String PUBLIC_KEY = "publicKey";

	private static final String PRIVATE_KEY = "privateKey";

	@Value("${docusign.client.id}")
	private String integrationKey;

	@Value("${docusign.api.username}")
	private String apiUserName;

	@Value("${docusign.url.path:account-d.docusign.com}")
	private String urlPath;

	@Autowired
	private DocuSignTokenInterceptor docuSignTokenInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(docuSignTokenInterceptor).addPathPatterns("/jwt/**");
	}

	@Bean
	public ApiClient apiClient() {
		return new ApiClient();
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
	public JWTGrantToken jwtgrantToken() {
		Security.addProvider(new BouncyCastleProvider());

		Long currentTimeMs = System.currentTimeMillis();
		Date issuedAt = new Date(currentTimeMs);
		Date expiresAt = new Date(currentTimeMs + 1 * 3600000);
		String scope = SIGNATURE_IMPERSONATION;

		return new JWTGrantToken(integrationKey, apiUserName, issuedAt, expiresAt, scope, urlPath,
				this.getTokenAlgorithm());
	}

	private Algorithm getTokenAlgorithm() {
		Map<String, String> keys = DocuSignUtils.getKeys();

		RSAPrivateKey privateKey = null;
		RSAPublicKey publicKey = null;
		try {
			privateKey = DocuSignUtils.getPrivateKey(keys.get(PRIVATE_KEY));

			publicKey = DocuSignUtils.getPublicKey(keys.get(PUBLIC_KEY));

		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}

		return Algorithm.RSA256(publicKey, privateKey);
	}

}
