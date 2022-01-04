package com.example.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.demo.model.UserInfoResponse;

@FeignClient(name = "docuSignAccountUserInfoApi", url = "${docusign.account.endpoint:https://account-d.docusign.com}")
public interface UserInfoApi {

	@GetMapping(value = "/oauth/userinfo", produces = { "application/json" })
	ResponseEntity<UserInfoResponse> getUserInfo(@RequestHeader("Authorization") String bearerToken);

}
