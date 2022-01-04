package com.example.demo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocusignService {

	@Autowired
	private EnvelopeService envelopeService;

	public void createEnvelope() {
		try {
			envelopeService.createEnvelope();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
