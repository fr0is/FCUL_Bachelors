package pleilist.app.facade.handlers;

import pleilist.app.domain.catalogos.CatalogoUtilizadores;

public class RegistarUtilizadorHandler {
	
	private CatalogoUtilizadores catUtilizador;
	
	public RegistarUtilizadorHandler(CatalogoUtilizadores catUsers) {
		this.catUtilizador = catUsers;
	}
	
	/**
	 * Regista um utilizador normal.
	 * @param Username
	 * @param Password
	 * @ensures existe um utilizador com esse username
	 */
	public void registarUtilizador(String username, String password) {
		this.catUtilizador.adicionarUtilizador(username, password);//acho que eh soh isto
	}
	
	/**
	 * Regista um utilizador com nivel de curador.
	 * @param Username
	 * @param Password
	 * @ensures existe um utilizador com esse username, com nivel de curador
	 */
	public void registarCurador(String username, String password) {
		this.catUtilizador.adicionarCurador(username, password);
	}

}
