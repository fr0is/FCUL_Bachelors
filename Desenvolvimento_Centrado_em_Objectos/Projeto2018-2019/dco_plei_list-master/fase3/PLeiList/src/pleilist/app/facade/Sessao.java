package pleilist.app.facade;

import pleilist.app.domain.Utilizador;

public class Sessao {

	private Utilizador utilizador;

	public Sessao(Utilizador u) {
		this.utilizador = u;
	}

	public Utilizador getUtilizador() {
		return this.utilizador;
	}

}
