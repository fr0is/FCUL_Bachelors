package oochess.app.facade;

import oochess.app.domain.Utilizador;
import oochess.app.domain.catalogos.CatalogoPartidas;
import oochess.app.domain.catalogos.CatalogoTorneios;
import oochess.app.domain.catalogos.CatalogoUtilizadores;
import oochess.app.facade.handlers.DesafiarHandler;
import oochess.app.facade.handlers.ProcessarDesafiosHandler;
import oochess.app.facade.handlers.RegistarResultadoHandler;

public class Sessao {
	
	private Utilizador utilizadorCorr;
	private CatalogoUtilizadores catUtl;
	private CatalogoTorneios catTorn;
	private CatalogoPartidas catPart;
	
	
	public Sessao(Utilizador user, CatalogoPartidas catPart, CatalogoUtilizadores catUtl,CatalogoTorneios catTorn) {
		this.utilizadorCorr = user;
		this.catPart = catPart;
		this.catUtl = catUtl;
		this.catTorn = catTorn;
	}

	public Utilizador getUtilizador() {
		return utilizadorCorr;
	}

	public CatalogoUtilizadores getCatUtl() {
		return catUtl;
	}

	public CatalogoTorneios getCatTorn() {
		return catTorn;
	}

	public CatalogoPartidas getCatPart() {
		return catPart;
	}

	//UC 5-49442
	public DesafiarHandler getDesafioParaPartidaHandler() {
		return new DesafiarHandler(this); 
	}

	
	//UC 6-51050
	public RegistarResultadoHandler getRegistarResultadoDePartida() {
		return new RegistarResultadoHandler(this);
	}

	public ProcessarDesafiosHandler getProcessarDesafios() {
		return new ProcessarDesafiosHandler(this); 
	}

}
