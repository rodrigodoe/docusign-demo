package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.integration.UserInfoIntegration;
import com.example.demo.model.AccountsResponse;
import com.example.demo.model.TokenResponse;
import com.example.demo.model.UserInfoResponse;

@Service
public class UserInfoService {

	@Autowired
	private UserInfoResponse userInfoResponse;

	@Autowired
	private TokenResponse tokenResponse;

	@Autowired
	private UserInfoIntegration userInfoIntegration;

	private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

	public void getUserInfo() {
		logger.info("accessToken => {}", tokenResponse.getAcessToken());
		UserInfoResponse response = this.userInfoIntegration.getUserInfo(tokenResponse.getAcessToken());
		this.build(response);

	}

	private AccountsResponse getDefaultAccount(List<AccountsResponse> accounts) {
		return accounts.stream().filter(AccountsResponse::isDefault).findFirst().orElse(null);
	}

	public void build(UserInfoResponse userInfo) {

		if (userInfo != null) {

			List<AccountsResponse> accounts = userInfo.getAccounts();

			userInfoResponse.setAccounts(accounts);
			userInfoResponse.setCreatedAt(userInfo.getCreatedAt());
			userInfoResponse.setEmail(userInfo.getEmail());
			userInfoResponse.setFamilyName(userInfo.getFamilyName());
			userInfoResponse.setGivenName(userInfo.getGivenName());
			userInfoResponse.setName(userInfo.getName());
			userInfoResponse.setSub(userInfo.getSub());
			userInfoResponse.setDefaultAccount(getDefaultAccount(accounts));
		}

	}

}
