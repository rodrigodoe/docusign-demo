package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsResponse {

	@JsonProperty("account_id")
	private String accountId;

	@JsonProperty("is_default")
	private boolean isDefault;

	@JsonProperty("account_name")
	private String accountName;

	@JsonProperty("base_uri")
	private String baseUri;
}
