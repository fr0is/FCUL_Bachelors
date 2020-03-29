package pleilist.app.facade.handlers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pleilist.app.domain.Clip;
import pleilist.app.domain.Curador;
import pleilist.app.domain.Hashtag;
import pleilist.app.domain.PlaylistDomain;
import pleilist.app.domain.Stream;
import pleilist.app.domain.Video;
import pleilist.app.domain.catalogos.BibliotecaGeral;
import pleilist.app.domain.catalogos.CatalogoHashtags;
import pleilist.app.domain.catalogos.CatalogoPlaylists;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.dto.Entrada;
import pleilist.app.facade.dto.Pair;
import pleilist.app.facade.exceptions.NoSuchVideoCodeException;
import pleilist.app.facade.exceptions.NoSuchVideoWithHashtagException;

public class CriarPlayListHandler {

	private PlaylistDomain playlistCorrente;
	private CatalogoPlaylists catPlaylists;
	private CatalogoHashtags catHashtags;
	private BibliotecaGeral bibGeral;
	private Curador curAutenticado;
	private Video vidCorrente;

	/**
	 * 
	 * @param s
	 * @param bibGeral
	 * @param catHashtags
	 * @param catPlaylists
	 * @requires s.getUtilizador() instanceof Curador && s != NULL && bibGeral != NULL 
	 * && catHashtags != NULL && catPlaylist != NULL
	 * @ensures eh criada uma Playlist
	 */
	public CriarPlayListHandler(Sessao s, BibliotecaGeral bibGeral, CatalogoHashtags catHashtags, CatalogoPlaylists catPlaylists) {
		//playlistCorrente soh eh inicializada no iniciaCriacao()
		this.bibGeral = bibGeral;
		this.catHashtags = catHashtags;
		this.catPlaylists = catPlaylists;
		this.curAutenticado = (Curador) s.getUtilizador();
		//vidCorrente so eh inicializado em indicarCodigo()
	}

	/**
	 * inicia a criacao da Playlist
	 * @param nomePlaylist - nome para atribuir ah playlist
	 * @requires  curAuntenticado != NULL && nomePlaylist != NULL
	 * @ensures eh criada uma playlist que passa a ser a playlist corrente com nome = nomePlaylist
	 */
	public void iniciaCriacao(String nomePlaylist) {
		this.playlistCorrente = this.catPlaylists.criarPlaylist(nomePlaylist);//UC2-1f-1
	}

	/**
	 * devolve uma lista de items e a hora de inicio de cada um dos videos da playlist
	 * @requires  curAuntenticado != NULL
	 * @return lista de Entradas
	 */
	public List<Entrada> obterEntradasActuais() {
		List<Entrada> entradas = this.playlistCorrente.getEntradas();//UC2-1v-1
		return entradas;
	}

	/**
	 * Devolve a lista de videos com uma determinada hashtag.
	 * @param tag hashTag de videos a procurar
	 * @requires curAuntenticado != NULL
	 * @return uma lista de pares Titulo, Codigo
	 * @throws NoSuchVideoWithHashtagException 
	 */
	public List<Pair<String, String>> obterVideosComHashtag(String tag) throws NoSuchVideoWithHashtagException {
		List<Pair<String, String>> vidsComHashtag = new ArrayList<>();
		Optional<Hashtag> h = this.catHashtags.getHashtag(tag);//UC2-2f-1
		if(h.isPresent()) {//UC2-2f-2 e UC2-2f-3 e UC2-2f-4
			Hashtag hashtag = h.get();

			List<Video> vidsComHtgBibGeral = this.bibGeral.getVideos(hashtag);//UC2-2f-2
			List<Pair<String,String>> id1 = new ArrayList<>();//passei a criar os pares no handler para poder usar o metodo getVideos(Hashtag h) na ChainedVideosStrategy
			for(Video vid: vidsComHtgBibGeral) {//UC2-2f-2.3 //antes estava na classe Biblioteca por isso eh que tem a numeracao errada
				Pair<String,String> par = new Pair<>(vid.getTitulo(), vid.getCodigo());//UC2-2f-2.4 //String eh imutavel, logo nao eh preciso fazer copia do titulo e do codigo
				id1.add(par);//UC2-2f-2.5
			}

			List<Video> vidsComHtgBibPessoal = this.curAutenticado.getVideos(hashtag);//UC2-2f-3
			List<Pair<String,String>> id2 = new ArrayList<>();//passei a criar os pares no handler para poder usar o metodo getVideos(Hashtag h) na ChainedVideosStrategy
			for(Video vid: vidsComHtgBibPessoal) {//UC2-2f-3.1.3 //antes estava na classe Biblioteca por isso eh que tem a numeracao errada
				Pair<String,String> par = new Pair<>(vid.getTitulo(), vid.getCodigo());//UC2-2f-3.1.4 //String eh imutavel, logo nao eh preciso fazer copia do titulo e do codigo
				id2.add(par);//UC2-2f-3.1.5
			}

			vidsComHashtag.addAll(id1);//UC2-2f-4
			vidsComHashtag.addAll(id2);//UC2-2f-4
		}
		if(vidsComHashtag.isEmpty()) {
			throw new NoSuchVideoWithHashtagException();
		}
		return vidsComHashtag;
	}
	
	public List<Pair<String, String>> pretendeVerTop10(String resposta) {
		if(resposta.equals("S")) {
			List<Pair<String,String>> result= new ArrayList<>();
			List<Video> top10 = this.bibGeral.getTop10Videos();
			for(Video v: top10) {
				String titulo = v.getTitulo();
				String codigo = v.getCodigo();
				Pair<String, String> par = new Pair<>(titulo, codigo);
				result.add(par);
			}
			return result;
		}else {
			return new ArrayList<>();
		}
	}

	/**
	 * Seleciona o video a adicionar com um determinado codigo.
	 * @param codigo
	 * @ensures o clip com o codigo = codigo eh associado ah playlistCorrente
	 * @return se o video eh uma stream ou nao
	 * @throws NoSuchVideoCodeException 
	 */
	public boolean indicarCodigo(String codigo) throws NoSuchVideoCodeException {
		Optional<Video> talvezVid = this.curAutenticado.getVideo(codigo);//UC2-2v-1
		if(!talvezVid.isPresent()) {//UC2-2v-2
			talvezVid = this.bibGeral.getVideo(codigo);//UC2-2v-2
		}
		if(talvezVid.isPresent()) {
			this.vidCorrente = talvezVid.get();
			boolean ehStream = this.vidCorrente.ehStream();//UC2-2v-3
			if(!ehStream) {//UC2-2v-4
				if(this.vidCorrente instanceof Clip) {
					Clip clip = (Clip) this.vidCorrente;
					this.playlistCorrente.adicionaClip(clip);//UC2-2v-4
				}
				return false;
			}
			return true;
		}else {
			throw new NoSuchVideoCodeException();
		}
	}

	/**
	 * Define a duracao de transmissao de uma stream
	 * @param duration - a duracao da Stream
	 * @requires curAutenticado != NULL && video para o qual se indica a duracao tem de ser uma Stream
	 * @ensures duracao da stream na playlistCorrente eh igual a duration
	 */
	public void indicaDuracao(Duration duration) {
		if(this.vidCorrente instanceof Stream) {
			Stream stream = (Stream) this.vidCorrente;
			this.playlistCorrente.adicionaStream(stream, duration);//UC2-3f-1
		}
	}

	/**
	 * termina a criacao de uma Playlist
	 * @requires curAutenticado != NULL
	 */
	public void terminar(){
		this.catPlaylists.adicionarPlaylist(this.playlistCorrente);//UC2-3v-1
	}

}
