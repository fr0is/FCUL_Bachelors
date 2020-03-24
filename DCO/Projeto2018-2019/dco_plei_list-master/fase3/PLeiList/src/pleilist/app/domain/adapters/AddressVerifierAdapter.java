package pleilist.app.domain.adapters;

import java.net.MalformedURLException;
import java.net.URL;

import net.padroesfactory.AddressVerifier;

public class AddressVerifierAdapter implements VerificadorEnderecos{

	@Override
	public boolean verificarEndereco(String address) {
			try {
				URL url = new URL(address);
				return AddressVerifier.verifyAddress(url);
			} catch (MalformedURLException e) {
				return false;
			}
	}
}