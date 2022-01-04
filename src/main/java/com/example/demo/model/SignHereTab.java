package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignHereTab {

	private String anchorString;
	private String anchorUnits;
	private String anchorXOffset;
	private String anchorYOffset;

}
