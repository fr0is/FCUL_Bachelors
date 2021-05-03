package oochess.app.facade.handlers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import oochess.app.domain.Desafio;
import oochess.app.domain.Partida;
import oochess.app.domain.Utilizador;
import oochess.app.domain.catalogos.CatalogoPartidas;
import oochess.app.domain.catalogos.CatalogoUtilizadores;
import oochess.app.domain.factories.EloStrategyFactory;
import oochess.app.elostrategies.EloStrategy;
import oochess.app.facade.Sessao;
import oochess.app.facade.exceptions.NoSuchMatchWithChallengeException;

public class RegistarResultadoHandler {
	
	private Utilizador uCorrente;
	private Utilizador uAdv;
	private Partida pCorrente;
	private CatalogoPartidas catPart;
	private CatalogoUtilizadores catUtl;

	
	/**
	 * UC6
	 * @param sessao
	 * @requires sessao != null
	 */
	public RegistarResultadoHandler(Sessao sessao) {
		this.uCorrente = sessao.getUtilizador();
		this.catPart = sessao.getCatPart();
		this.catUtl = sessao.getCatUtl();
	}
	
	/**
	 * UC6-1
	 * Indica o desafio correspondente a partida 
	 * @param codigoDesafio - codigo do desafio correspondente
	 * @throws NoSuchMatchWithChallengeException
	 * @requires codigoDesafio != null
	 */
	public void indicaDesafio(String codigoDesafio) throws NoSuchMatchWithChallengeException {	
		//1.1
		Optional<Desafio> existeDesafio = this.uCorrente.getDesafio(codigoDesafio);  
		//1.2
		existeDesafio.ifPresent((Desafio d) -> 
			this.pCorrente = this.catPart.getPartida(d.getCodigo(), this.uCorrente.getUsername())
		);
		if(this.pCorrente == null) {
			throw new NoSuchMatchWithChallengeException();
		}
	}

	/**
	 * UC6-1
	 * Inicia criacao de uma partida espontanea
	 * @return Lista dos 5 ultimos utilizadores jogados
	 */
	public List<Utilizador> indicaPartidaEspontanea() {
		//1.3
		return this.catPart.getUltimos5Jogos(this.uCorrente.getUsername(),this.catUtl);
	}
	
	/**
	 * UC6-2
	 * Indica detalhes da partida espontanea
	 * @param username - username do utilizador adversario
	 * @param datahora - data e hora da partida
	 * @requires username != null && datahora != null
	 * @ensures p.userAdv = u:Utilizador tal que u.username = username && p.dataHora = datahora
	 */
	public void indicaDetalhes(String username, LocalDateTime datahora) {
		//2.1
		this.uAdv = this.catUtl.getUtilizador(username);
		//2.2
		this.pCorrente = this.catPart.criaPartida(this.uCorrente.getUsername(), username, datahora);                                    
	}
	
	/**
	 * UC6-3
	 * Indica resultado da partida
	 * @param resultado - resultado da partida
	 * @requires resultado != null
	 * @ensures uCorr.elo = novoElo && uAdv.elo = novoElo
	 * @return novo elo do utilizador corrente
	 */
	public double indicarResultado(String resultado) {
		//3.1
		this.pCorrente.setResultado(resultado.toUpperCase());
		//Obter uAdv
		if(this.pCorrente.getUser1().contentEquals(this.uCorrente.getUsername())) {
			Utilizador userAdv = this.catUtl.getUtilizador(this.pCorrente.getUser2());
			this.uAdv = userAdv;
		}else {
			Utilizador userAdv = this.catUtl.getUtilizador(this.pCorrente.getUser1());
			this.uAdv = userAdv;
		}
		//3.2
		this.catPart.atualizaPartida(this.pCorrente,this.uCorrente,this.uAdv);
		//3.3
		EloStrategyFactory factory = EloStrategyFactory.getInstance();
		EloStrategy strat = factory.getEloStrategy();
		return strat.calcularElo(this.uCorrente,this.uAdv, resultado);
	}
}
