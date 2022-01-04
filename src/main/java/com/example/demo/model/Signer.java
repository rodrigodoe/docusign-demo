package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Signer {

	private String email;
	private String name;
	private String recipientId;
	private String routingOrder;
	private Tabs tabs;

}
