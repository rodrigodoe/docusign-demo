package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvelopeResponse {
	private String envelopeId;
	private String uri;
	private String statusDateTime;
	private String status;
}
