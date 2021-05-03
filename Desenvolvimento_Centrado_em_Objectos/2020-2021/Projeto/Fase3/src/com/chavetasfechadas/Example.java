package com.chavetasfechadas;

public class Example {
	public static void main(String[] args) {
		JDAMock c = new JDAMockBuilder().createDefault("3423134134").disableCache(true).setCompression(true).setActivity("Playing chess").build();
		c.sendMessage("JoaoRock", "Vou dar Xeque-Mate!");
	}
}
