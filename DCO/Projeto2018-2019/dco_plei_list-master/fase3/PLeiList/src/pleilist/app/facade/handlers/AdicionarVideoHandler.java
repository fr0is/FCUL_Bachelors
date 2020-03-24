package pleilist.app.facade.handlers;

import java.time.Duration;

import pleilist.app.domain.Clip;
import pleilist.app.domain.Curador;
import pleilist.app.domain.Hashtag;
import pleilist.app.domain.Utilizador;
import pleilist.app.domain.Video;
import pleilist.app.domain.adapters.VerificadorEnderecos;
import pleilist.app.domain.catalogos.Biblioteca;
import pleilist.app.domain.catalogos.BibliotecaGeral;
import pleilist.app.domain.catalogos.CatalogoHashtags;
import pleilist.app.domain.factories.VerificadorEnderecosFactory;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.exceptions.NoSuchAddressException;

public class AdicionarVideoHandler {

	private BibliotecaGeral bibGeral;
	private Video vidCorrente;
	private CatalogoHashtags catHtgs;
	private Biblioteca bib;//vai gerar codigos para os videos de modo a que nao haja 2 videos com o mesmo codigo, mesmo se estiverem em bibliotecas diferentes e criar os videos
	private Curador curAutenticado;
	
	/**
	 * 
	 * @param s
	 * @param bib
	 * @param bibGeral
	 * @param catHashtags
	 * @requires s!= NULL && bib != NULL 
	 * && bibGeral != NULL && catHashTags != NULL 
	 * && s.getUtilizador() instanceof Utilizador
	 */
	public AdicionarVideoHandler(Sessao s, Biblioteca bib, BibliotecaGeral bibGeral, CatalogoHashtags catHashtags) {
		this.bib = bib;
		this.bibGeral = bibGeral;
		//vidCorrente soh eh inicializado mais tarde
		this.catHtgs = catHashtags;
		Utilizador utilizador = s.getUtilizador();
		if(utilizador instanceof Curador) {
			this.curAutenticado = (Curador) s.getUtilizador();
		}
	}

	/**
	 * Inicia adicao de um video
	 * @requires curAutenticado != NULL
	 */
	public void iniciarAdicionar() {
		System.out.println("Adicao do video comecou!!!"); //UC1-1f-1
	}

	/**
	 * Define o tipo do video
	 * @param isClip - se o video a ser adicionado eh um clip (true) ou uma stream (false)
	 * @requires curAutenticado != NULL
	 * @ensures É criado um Clip ou uma Stream consoante isClip = clip
	 * ou isClip = stream, respetivamente 
	 */
	public void definirComoClip(boolean isClip) {
		this.vidCorrente = bib.criarVideo(isClip);//UC1-1v-1
	}

	/**
	 * Define o titulo e endereco do video corrente
	 * @param title - o titulo do video corrente
	 * @param address - o endereco do video corrente
	 * @throws NoSuchAddressException 
	 * @requires title != null && address != null && curAutenticado != NULL
	 * @ensures v.title = title e v.address = address
	 */
	public void indicaVideo(String title, String address) throws NoSuchAddressException {
		VerificadorEnderecosFactory factory = VerificadorEnderecosFactory.getInstance();
		VerificadorEnderecos vEnd = factory.getVerifEnderecos();
		if(vEnd.verificarEndereco(address)) {
			this.vidCorrente.defTitleAndAddress(title, address);//UC1-2f-2
		}else {
			throw new NoSuchAddressException();
		}
	}
	
	/**
	 * Define a duracao do clip corrente
	 * @param duration - duracao do clip corrente
	 * @requires duration != null && curAutenticado != NULL
	 * @ensures c.duration = duration
	 */
	public void indicaDuracao(Duration duration) {
		if(this.vidCorrente instanceof Clip) {
			((Clip) this.vidCorrente).defDuration(duration);//UC1-2v-1
		}
	}

	/**
	 * Associa uma hashtag ao video corrente
	 * @param tag - nome da hashtag a associar ao video corrente
	 * @requires tag != null && curAutenticado != NULL
	 * @ensures tag eh associado ao video corrente
	 */
	public void indicaTag(String tag) {
		Hashtag h = catHtgs.encontraHashtag(tag);//UC1-3f-1
		this.vidCorrente.adicionarHashtag(h);//UC1-3f-2
	}

	/**
	 * Termina a adicao do video.
	 * @param ePublico indica se o video eh publico ou privado
	 * @requires curAutenticado != NULL
	 * @ensures ePublico == True -> define privacidade do video como publica
	 * ePublico == false -> define privaciade do video como privada
	 * @return Codigo do video criado
	 */
	public String defineComoPublico(boolean ePublico) {
		this.vidCorrente.indicaPrivacidade(ePublico);//UC1-3v-1
		String codigo = this.bib.novoCodigo();//UC1-3v-2
		if(!ePublico) {
			this.curAutenticado.adicionaVideo(this.vidCorrente, codigo);//UC1-3v-3a
		}else {
			this.bibGeral.adicionaVideo(this.vidCorrente, codigo);//UC1-3v-3b
		}
		return codigo;//String eh imutavel, nao nos temos de preocupar em devolver copia
	}

}
