package pleilist.app.domain.factories;

import pleilist.app.domain.Configuration;
import pleilist.app.domain.adapters.VerificadorEnderecos;

public class VerificadorEnderecosFactory {

	private static final String ADDRESSVERIFIER_KEY = "addressVerifier";

	private VerificadorEnderecos defaultVerificadorEnderecos = new VerificadorEnderecos() {
		@Override
		public boolean verificarEndereco(String address) {
			return false;
		}
	};
	
	private VerificadorEnderecos vEnd;

	private static VerificadorEnderecosFactory INSTANCE = null;

	public static VerificadorEnderecosFactory getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new VerificadorEnderecosFactory();
		}
		return INSTANCE;
	}
	
	private VerificadorEnderecosFactory() {
		Configuration conf = Configuration.getInstance();
		this.vEnd = conf.<VerificadorEnderecos>createInstanceFromKey(ADDRESSVERIFIER_KEY,
				defaultVerificadorEnderecos);
	}
	
	public VerificadorEnderecos getVerifEnderecos() {
		return this.vEnd;
	}
}

