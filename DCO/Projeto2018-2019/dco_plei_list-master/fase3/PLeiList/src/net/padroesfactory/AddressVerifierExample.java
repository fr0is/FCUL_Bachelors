package net.padroesfactory;

import java.net.MalformedURLException;
import java.net.URL;

public class AddressVerifierExample {

	public static void main(String[] args) {
		AddressVerifier c = new AddressVerifier();
		try {
			System.out.println(c.verifyAddress(new URL("https://ciencias.ulisboa.pt/")) == true);
			System.out.println(c.verifyAddress(new URL("https://sem.ciencias.ulisboa.pt/")) == false);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
