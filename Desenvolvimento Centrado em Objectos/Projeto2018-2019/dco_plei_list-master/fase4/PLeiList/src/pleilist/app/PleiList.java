package pleilist.app;

import java.util.Optional;

import pleilist.app.domain.Utilizador;
import pleilist.app.domain.catalogos.Biblioteca;
import pleilist.app.domain.catalogos.BibliotecaGeral;
import pleilist.app.domain.catalogos.CatalogoHashtags;
import pleilist.app.domain.catalogos.CatalogoPlaylists;
import pleilist.app.domain.catalogos.CatalogoUtilizadores;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.handlers.AdicionarVideoHandler;
import pleilist.app.facade.handlers.CriarPlayListHandler;
import pleilist.app.facade.handlers.CriarPlaylistAutoHandler;
import pleilist.app.facade.handlers.RegistarUtilizadorHandler;
import pleilist.app.facade.handlers.VerPlaylistHandler;

/**
 * Esta eh a classe do sistema.
 */
public class PleiList {
	
	private CatalogoUtilizadores catUtilizadores;
	private Biblioteca bib;
	private BibliotecaGeral bibGeral;
	private CatalogoHashtags catHashtags;
	private CatalogoPlaylists catPlaylists;
	
	public PleiList() {
		//Startup Use Case
		this.catUtilizadores = CatalogoUtilizadores.getInstance();
		this.bib = Biblioteca.getInstance();
		this.bibGeral = BibliotecaGeral.getInstance();
		this.catHashtags = CatalogoHashtags.getInstance();
		this.catPlaylists = CatalogoPlaylists.getInstance();
	}


	public RegistarUtilizadorHandler getRegistarUtilizadorHandler() {
		return new RegistarUtilizadorHandler(this.catUtilizadores);
	}
	
	/**
	 * Returns an optional Session representing the authenticated user.
	 * @param username
	 * @param password
	 * @return
	 */
	public Optional<Sessao> autenticar(String username, String password) {
		Optional<Utilizador> talvezUtilizador = this.catUtilizadores.getUtilizador(username, password);
		return talvezUtilizador.map((u) -> new Sessao(u));
	}

	public AdicionarVideoHandler getAdicionarVideoHandler(Sessao s) {
		return new AdicionarVideoHandler(s, this.bib, this.bibGeral, this.catHashtags);
	}

	public CriarPlayListHandler getCriarPlayListHandler(Sessao s) {
		return new CriarPlayListHandler(s, this.bibGeral, this.catHashtags, this.catPlaylists);
	}

	public VerPlaylistHandler getVerPlaylistHandler(Sessao s) {
		return new VerPlaylistHandler(s, this.catHashtags, this.catPlaylists);
	}
	
	public CriarPlaylistAutoHandler getCriarPlaylistAutoHandler() {
		return new CriarPlaylistAutoHandler(this.catPlaylists);
	}
	
}
