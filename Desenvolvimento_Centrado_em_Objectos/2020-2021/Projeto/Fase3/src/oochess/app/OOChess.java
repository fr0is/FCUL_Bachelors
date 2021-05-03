package oochess.app;

import java.util.Optional;

import oochess.app.domain.Utilizador;
import oochess.app.domain.catalogos.CatalogoPartidas;
import oochess.app.domain.catalogos.CatalogoTorneios;
import oochess.app.domain.catalogos.CatalogoUtilizadores;
import oochess.app.facade.Sessao;
import oochess.app.facade.handlers.RegistarUtilizadorHandler;

/**
 * Esta é a classe do sistema.
 */
public class OOChess {
	
	private CatalogoUtilizadores catUtl;
	private CatalogoPartidas catPart;
	private CatalogoTorneios catTorn;
	
	public OOChess() {
		this.catUtl = CatalogoUtilizadores.getInstance();
		this.catPart = CatalogoPartidas.getInstance();
		this.catTorn = CatalogoTorneios.getInstance();
	}

	public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return new RegistarUtilizadorHandler(this);
	}
	
	public Optional<Sessao> autenticar(String username, String password) {
		Optional<Utilizador> user = this.catUtl.getUtilizador(username,password);
		return user.map(u -> new Sessao(u,this.catPart,this.catUtl,this.catTorn)); 
	}

	public CatalogoUtilizadores getCatalogoUtilizadores() {
		return this.catUtl;
	}
	
	public CatalogoPartidas getCatPart() {
		return this.catPart;
	}
	
}
