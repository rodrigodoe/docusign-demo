package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.demo.integration.EnvelopeIntegration;
import com.example.demo.model.AccountsResponse;
import com.example.demo.model.Document;
import com.example.demo.model.EnvelopeDefinition;
import com.example.demo.model.EnvelopeResponse;
import com.example.demo.model.Recipients;
import com.example.demo.model.SignHereTab;
import com.example.demo.model.Signer;
import com.example.demo.model.Tabs;
import com.example.demo.model.TokenResponse;
import com.example.demo.model.UserInfoResponse;
import com.example.demo.utils.Base64EncodePdf;

@Service
public class EnvelopeService {

	private static final Logger logger = LoggerFactory.getLogger(EnvelopeService.class);

	@Autowired
	private TokenResponse tokenResponse;

	@Autowired
	private UserInfoResponse userInfoResponse;

	@Autowired
	private EnvelopeIntegration envelopeIntegration;

	public void createEnvelope() throws IOException {

		File file = new ClassPathResource("World_Wide_Corp_lorem.pdf").getFile();

		String base64document = Base64EncodePdf.encode(file);

		AccountsResponse account = userInfoResponse.getDefaultAccount();

		String accountId = account == null ? null : account.getAccountId();

		EnvelopeDefinition definition = createEnvelopeDefinition(base64document);

		EnvelopeResponse envelope = envelopeIntegration.createEnvelope(tokenResponse.getAcessToken(), accountId,
				definition);

		logger.info("EnvelopeResponse =>> {}", envelope);

	}

	private EnvelopeDefinition createEnvelopeDefinition(String base64document) {

		EnvelopeDefinition definition = new EnvelopeDefinition();
		definition.setEmailSubject("Please sign this document set");

		Document document = new Document();
		document.setDocumentBase64(base64document);
		document.setName("document");
		document.setFileExtension("pdf");
		document.setDocumentId("1");

		Signer signer = new Signer();
		signer.setEmail("rrodgcar@gmail.com");
		signer.setName("Rodrigo Carvalho");
		signer.setRecipientId("1");
		signer.setRoutingOrder("1");
		Tabs tabs = new Tabs();
		SignHereTab signHeretab = new SignHereTab();
		signHeretab.setAnchorString("**signature_1**");
		signHeretab.setAnchorUnits("pixels");
		signHeretab.setAnchorXOffset("20");
		signHeretab.setAnchorYOffset("10");
		tabs.setSignHereTabs(Arrays.asList(signHeretab));
		signer.setTabs(tabs);
		Recipients recipient = new Recipients();
		recipient.setSigners(Arrays.asList(signer));
		definition.setRecipients(recipient);
		definition.setDocuments(Arrays.asList(document));
		definition.setStatus("sent");

		return definition;
	}

}
