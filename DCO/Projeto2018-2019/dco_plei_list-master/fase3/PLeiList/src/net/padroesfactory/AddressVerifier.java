package net.padroesfactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddressVerifier {

	public static boolean verifyAddress(URL url) {
		HttpURLConnection httpURLConnection;
		try {
			httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("HEAD");
			httpURLConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
			int responseCode = httpURLConnection.getResponseCode();
			return responseCode == HttpURLConnection.HTTP_OK;
		} catch (IOException e) {
			return false;
		}

	}
}
