package com.example.demo.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnvelopeDefinition {

	private String emailSubject;
	private List<Document> documents;
	private Recipients recipients;
	private String status;

}
