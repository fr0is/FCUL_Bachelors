package pleilist.app.domain.catalogos;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pleilist.app.domain.Curador;
import pleilist.app.domain.Utilizador;

public class CatalogoUtilizadores {

	private Map<String,Utilizador> utilizadores;

	private CatalogoUtilizadores() {
		this.utilizadores = new HashMap<>();
	}

	private static CatalogoUtilizadores INSTANCE = null;

	public static CatalogoUtilizadores getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new CatalogoUtilizadores();
		}
		return INSTANCE;
	}

	public void adicionarUtilizador(String username, String password) {
		Optional<Utilizador> talvezUtilizador = Optional.ofNullable(this.utilizadores.get(username));
		if(!talvezUtilizador.isPresent()) {
			this.utilizadores.put(username, new Utilizador(username, password));
		}else {
			System.out.println("Jah existe um utilizador com esse username!");
		}

	}

	public void adicionarCurador(String username, String password) {
		Optional<Utilizador> talvezUtilizador = Optional.ofNullable(this.utilizadores.get(username));
		if(!talvezUtilizador.isPresent()) {
			this.utilizadores.put(username, new Curador(username, password));
		}else {
			System.out.println("Jah existe um utilizador com esse username!");
		}
	}

	public Optional<Utilizador> getUtilizador(String username, String password) {
		for (Utilizador u: this.utilizadores.values()) {
			if (u.autenticaCom(username, password)) {
				return Optional.of(u);
			}
		}
		return Optional.empty();
	}

}
