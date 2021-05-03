package oochess.app.facade.handlers;

import oochess.app.OOChess;
import oochess.app.domain.Utilizador;
import oochess.app.domain.catalogos.CatalogoPartidas;
import oochess.app.domain.catalogos.CatalogoUtilizadores;
import oochess.app.domain.factories.EloStrategyFactory;
import oochess.app.elostrategies.EloStrategy;
import oochess.app.facade.exceptions.UserAlreadyExistsException;

public class RegistarUtilizadorHandler {

	private CatalogoUtilizadores catUtl;
	private CatalogoPartidas catPart;

	
	/**
	 * 
	 * @param ooChess
	 * @requires ooChess!=null
	 */
	public RegistarUtilizadorHandler(OOChess ooChess) {
		this.catUtl = ooChess.getCatalogoUtilizadores();
		this.catPart = ooChess.getCatPart();
	}

	/**
	 * Regista um utilizador normal
	 * @param discordID - ID de discord do novo utilizador
	 * @param Username - username do novo utilizador
	 * @param Password - password do novo utilizador
	 * @requires username != null && password != null && discordID != null
	 * @throws UserAlreadyExistsException 
	 * @ensures existe u:Utilizador tal que u.username=username && u.password=password && u.discordID=discordID
	 */
	public void registarUtilizador(String username, String password, String discordID) throws UserAlreadyExistsException{
		EloStrategyFactory factory = EloStrategyFactory.getInstance();
		EloStrategy strat = factory.getEloStrategy();
		Utilizador userNovo = new Utilizador(username,password,discordID,strat.criarElo());
		if(this.catUtl.getUtilizador(username) != null) {
			throw new UserAlreadyExistsException();
		}else {
			this.catUtl.adicionarUtilizador(userNovo);
			this.catPart.adicionarUtilizadorListaPartidas(userNovo);
		}
	}

}
