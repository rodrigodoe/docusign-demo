package com.example.demo.utils;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class Base64EncodePdf {

	public static String encode(File file) {
		String b64 = null;
		try {
			byte[] bytes = Files.readAllBytes(file.toPath());

			b64 = Base64.getEncoder().encodeToString(bytes);
			System.out.println(b64);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b64;
	}
}
