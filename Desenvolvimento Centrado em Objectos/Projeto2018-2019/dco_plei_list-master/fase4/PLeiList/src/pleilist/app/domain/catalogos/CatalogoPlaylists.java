package pleilist.app.domain.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import pleilist.app.domain.Hashtag;
import pleilist.app.domain.PlaylistDomain;

public class CatalogoPlaylists {

	private int nextCodigo = 0;

	private Map<String,PlaylistDomain> playlists;
	private Map<Hashtag, List<PlaylistDomain>> playlistsComHtg;

	private CatalogoPlaylists() {
		this.playlists = new HashMap<>();
		this.playlistsComHtg = new HashMap<>();
	}

	private static CatalogoPlaylists INSTANCE = null;

	public static CatalogoPlaylists getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new CatalogoPlaylists();
		}
		return INSTANCE;
	}

	/**
	 *
	 * @param nomePlaylist
	 * @return
	 */
	public PlaylistDomain criarPlaylist(String nomePlaylist) {
		String codigo = novoCodigo();//UC2-1f-1.1
		return new PlaylistDomain(nomePlaylist, codigo);
	}

	private String novoCodigo() {
		String cod = "play_" + Integer.toString(this.nextCodigo);
		this.nextCodigo++;
		return cod;
	}

	public void adicionarPlaylist(PlaylistDomain pl) {
		pl.atualizaClassMedia();//caso os videos inseridos ja tenham uma classificacao quando sao adicionados ah playlist
		String codigo = pl.getCodigo();//UC2-3v-1.1
		this.playlists.put(codigo, pl);//UC2-3v-1.2
		Set<Hashtag> hashtags = pl.getHashtags();//UC2-3v-1.3
		for(Hashtag h: hashtags) {
			Optional<List<PlaylistDomain>> talvezPlaylistsComHtg = Optional.ofNullable(this.playlistsComHtg.get(h));
			List<PlaylistDomain> playlistsComHtg = talvezPlaylistsComHtg.orElse(new ArrayList<>());//UC2-3v-1.4
			playlistsComHtg.add(pl);//UC2-3v-1.5
			this.playlistsComHtg.put(h, playlistsComHtg);//UC2-3v-1.6
		}
	}

	public Optional<PlaylistDomain> getPlaylist(String codigo) {
		Optional<PlaylistDomain> talvezPlaylist = Optional.ofNullable(this.playlists.get(codigo));//UC4-1v-1.1
		if(talvezPlaylist.isPresent()) {
			return talvezPlaylist;
		}
		return Optional.empty();
	}

	public List<PlaylistDomain> getPlaylists(Hashtag h) {
		Optional<List<PlaylistDomain>> talvezPlaylistsComHtg = Optional.ofNullable(this.playlistsComHtg.get(h));
		if(!talvezPlaylistsComHtg.isPresent()) {
			System.out.println("Nao ha nenhuma playlist com esse hashtag");
		}
		List<PlaylistDomain> playlistsComHtg = talvezPlaylistsComHtg.orElse(new ArrayList<>());//UC4-1f-2.2

		//////////////////////////////////////////////////////////////
		/////////Imprimir informacao das playlists para debug/////////
		//////////////////////////////////////////////////////////////
		playlistsComHtg.stream().forEach((PlaylistDomain pl) -> {
			System.out.println(pl.toString());
		});
		/////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////

		return playlistsComHtg;
	}

}
