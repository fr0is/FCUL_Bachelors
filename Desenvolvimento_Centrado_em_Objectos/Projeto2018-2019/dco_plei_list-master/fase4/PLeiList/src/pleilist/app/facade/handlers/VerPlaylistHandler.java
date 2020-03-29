package pleilist.app.facade.handlers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pleilist.app.domain.Hashtag;
import pleilist.app.domain.Item;
import pleilist.app.domain.PlaylistDomain;
import pleilist.app.domain.Utilizador;
import pleilist.app.domain.Video;
import pleilist.app.domain.catalogos.CatalogoHashtags;
import pleilist.app.domain.catalogos.CatalogoPlaylists;
import pleilist.app.facade.Sessao;
import pleilist.app.facade.dto.Pair;
import pleilist.app.facade.dto.Playlist;

public class VerPlaylistHandler {

	private CatalogoHashtags catHashtags;
	private CatalogoPlaylists catPlaylists;
	private PlaylistDomain playlistCorrente;
	private int indiceAtual;
	private Item itCorrente;
	private Utilizador utilizadorAutenticado;

	/**
	 * 
	 * @param s - sessao
	 * @param catHashtags - catalogo de hashTags
	 * @param catPlaylists - catalogo de Playlists
	 * @param bibGeral - Biblioteca geral
	 * @requires s.getUtilizador() != null && catHashtags != null &&
	 * carPlaylists != null
	 */
	public VerPlaylistHandler(Sessao s, CatalogoHashtags catHashtags,
			CatalogoPlaylists catPlaylists) {
		this.catHashtags = catHashtags;
		this.catPlaylists = catPlaylists;
		//playlistCorrente soh eh inicializada no metodo escolhePlaylist()
		this.indiceAtual = 0;
		//vidCorrente soh eh inicializado no metodo verProximo()
		this.utilizadorAutenticado = s.getUtilizador();
	}

	/**
	 * procura os videos com uma determinada hashtag
	 * @param tag - nome da HashTag que procuramos
	 * @requires utilizadorAutenticado != NULL && tag != null
	 * @return lista de playlist com a HashTag = tag 
	 */
	public List<Playlist> procurarPorTag(String tag) {
		Optional<Hashtag> talvezHashtag = this.catHashtags.getHashtag(tag);//UC4-1f-1
		if(talvezHashtag.isPresent()) {//UC4-1f-2
			Hashtag h = talvezHashtag.get();
			List<PlaylistDomain> playlistsComHtg = this.catPlaylists.getPlaylists(h);//UC4-1f-2
			List<Playlist> result = new ArrayList<>();
			for(PlaylistDomain plDomain: playlistsComHtg) {
				Playlist pl = new Playlist(plDomain.getNome(), plDomain.getCodigo(), plDomain.getPontuacao());
				result.add(pl);
			}
			return result;
		}
		return new ArrayList<>();
	}

	/**
	 * procura a playlist com codigo = codigo
	 * @param codigo - codigo para escolher a playlist
	 * @requires utilizadorAutenticado != NULL && 
	 * codigo eh o codigo de uma das playlists devolvidas por procuraPorTag
	 * @return playlist com codigo = codigo
	 */
	public Duration escolhePlaylist(String codigo) {
		Optional<PlaylistDomain> talvezPlaylist = this.catPlaylists.getPlaylist(codigo);//UC4-1v-1
		if(talvezPlaylist.isPresent()) {//UC4-1v-2
			this.playlistCorrente = talvezPlaylist.get();
			Duration durTotal = this.playlistCorrente.getDurTotal();//UC4-1v-2 //Duration eh imutavel, nao nos temos de preocupar em devolver copia
			return durTotal;
		}
		return Duration.ZERO;
	}

	/**
	 * procura o proximo video
	 * @requires utilizadorAutenticado != NULL
	 * @ensures o item corrente eh atualizado
	 * @return devolve um par com o endereco do proximo video
	 *  e a sua duracao de transmissao, se a duracao for zero
	 *  eh porque ja nao ha mais videos para ver
	 */
	public Pair<String, Duration> verProximo() {
		int numItensPlaylist = this.playlistCorrente.getItens().size();
		if(this.indiceAtual <= numItensPlaylist - 1) { //se ainda houver videos para ver
			Item item = this.playlistCorrente.getProximoItem(this.indiceAtual);//UC4-2f-1
			this.indiceAtual++;
			this.itCorrente = item;
			Video vidCorrente = this.itCorrente.getVideo();//UC4-2f-2
			Duration durTransm = this.itCorrente.getDuracaoTrans();//UC4-2f-3
			Pair<String, Duration> enderecoDur = new Pair<>(vidCorrente.getEndereco(), durTransm);//UC4-2f-4 //String e Duration sao imutaveis, nao temos de nos preocupar em devolver copias do endereco e da duracao
			return enderecoDur;
		}else {
			this.indiceAtual = 0;//caso queira ver de novo
			return new Pair<>(null,Duration.ZERO);
		}

		
	}

	/**
	 * classificar o video
	 * @param estrelas - numero de estrelas 
	 * @requires utilizadorAutenticado != NULL
	 * @ensures eh criada ou atualizada a classificacao dada por este utilizador ao video
	 */
	public void classificar(int estrelas) {
		Video vidCorrente = this.itCorrente.getVideo();
		vidCorrente.classifica(this.utilizadorAutenticado, estrelas);//UC4-2V-1
		//UC4-2V-2 foi implementado com o padrão Observer
		vidCorrente.atualizaVisualizacoes();//UC4-2V-3
		//UC4-2v-4 so se vai calcular o top10 se for pedido
	}

}

