package pleilist.app.domain.adapters;

import com.chavetasfechadas.UrlChecker;

public class UrlCheckerAdapter implements VerificadorEnderecos{
	
	UrlChecker urlChecker = new UrlChecker();

	@Override
	public boolean verificarEndereco(String address) {
		urlChecker.setUrl(address);
		return urlChecker.validate();
	}
}
