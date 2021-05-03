package com.chavetasfechadas;


/*
 * Esta classe segue o padrão Builder
 */
public class JDAMockBuilder {
	
	private String token;
	private String activity = "";
	private boolean compression = true;
	private boolean cache = true;
	
	public JDAMockBuilder createDefault(String token) {
		this.token = token;
		return this;
	}
	public JDAMockBuilder disableCache(boolean b) {
		cache = b;
		return this;
	}
	public JDAMockBuilder setCompression(boolean b) {
		compression = b;
		return this;
	}
	public JDAMockBuilder setActivity(String s) {
		activity = s;
		return this;
	}
	
	
	public JDAMock build() {
		return new JDAMock(token, activity, cache, compression);
	}
}
