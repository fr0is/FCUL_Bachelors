package com.chavetasfechadas;

import java.net.HttpURLConnection;
import java.net.URL;

public class UrlChecker {
	// shamelessly curl'ed from https://singztechmusings.wordpress.com/2011/05/26/java-how-to-check-if-a-web-page-exists-and-is-available/ 
	
	String url;
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean validate() {
		HttpURLConnection httpUrlConn;
        try {
            httpUrlConn = (HttpURLConnection) new URL(url)
                    .openConnection();
            httpUrlConn.setRequestMethod("HEAD");
            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);
            return (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            return false;
        }
	}
}
