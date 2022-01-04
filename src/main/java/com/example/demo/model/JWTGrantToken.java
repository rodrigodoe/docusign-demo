package com.example.demo.model;

import java.util.Date;

import com.auth0.jwt.algorithms.Algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTGrantToken {

	private String iss;
	private String subject;
	private Date issuedAt;
	private Date expiresAt;
	private String scope;
	private String aud;
	private Algorithm algorithm;

}
