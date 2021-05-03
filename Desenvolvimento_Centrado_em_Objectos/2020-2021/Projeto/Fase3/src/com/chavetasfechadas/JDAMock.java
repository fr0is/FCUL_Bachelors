package com.chavetasfechadas;

public class JDAMock {
	// This library is inspired from the real JDA!
	// Source: https://github.com/DV8FromTheWorld/JDA
	
	
	public JDAMock(String token, String activity, boolean cache, boolean compression) {
		// Ignore arguments
	}

	public void sendMessage(String username, String message) {
		System.out.println("Sent via JDA to " + username  + ": " + message);
	}
}
