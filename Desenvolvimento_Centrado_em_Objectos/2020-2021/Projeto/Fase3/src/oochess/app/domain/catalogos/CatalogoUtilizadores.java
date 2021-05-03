package oochess.app.domain.catalogos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import oochess.app.domain.Utilizador;

public class CatalogoUtilizadores {
	private Map<String,Utilizador> listaUtilizadores;
	
	private static CatalogoUtilizadores INSTANCE;
	
	public static CatalogoUtilizadores getInstance() {
		if(INSTANCE == null) { //Lazy Loading
			INSTANCE = new CatalogoUtilizadores();
		}
		return INSTANCE;
		
	}
	
	protected CatalogoUtilizadores() {
		this.listaUtilizadores = new HashMap<>();
	}
	
	/**
	 * UC6-2.1.1 Obter u:Utilizador tal que u.username = username
	 * @param username
	 * @return 
	 */
	public Utilizador getUtilizador(String username) {
		return this.listaUtilizadores.get(username);
	}

	public void adicionarUtilizador(Utilizador userNovo) {
		this.listaUtilizadores.put(userNovo.getUsername(),userNovo);
	}
	
	public Map<String,Utilizador> getMap(){
		return this.listaUtilizadores;
	}

	public Optional<Utilizador> getUtilizador(String username, String password) {
		Utilizador u = this.listaUtilizadores.get(username);
		if(u.autentica(password)) {
			return Optional.of(u);
		}
		return Optional.empty();
	}

}
