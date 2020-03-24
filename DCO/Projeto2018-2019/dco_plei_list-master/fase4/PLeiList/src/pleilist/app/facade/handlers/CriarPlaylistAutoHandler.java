package pleilist.app.facade.handlers;

import java.time.Duration;
import java.util.List;

import pleilist.app.domain.Clip;
import pleilist.app.domain.Configuration;
import pleilist.app.domain.Stream;
import pleilist.app.domain.PlaylistDomain;
import pleilist.app.domain.Video;
import pleilist.app.domain.catalogos.CatalogoPlaylists;
import pleilist.app.domain.factories.StrategyFactory;
import pleilist.app.domain.strategies.Strategy;

public class CriarPlaylistAutoHandler {	

	private static final int STREAM_MINUTES = 30;
	private static final String STRATEGIES_KEY = "strategies";
	
	private CatalogoPlaylists catPlaylists;
	private Configuration conf;
	private PlaylistDomain playlistCorrente;
	
	public CriarPlaylistAutoHandler(CatalogoPlaylists catPlaylists) {
		this.catPlaylists = catPlaylists;
		this.conf = Configuration.getInstance();
	}
	
	public List<String> iniciaCriacaoAutoPlaylist(String nome){
		this.playlistCorrente = this.catPlaylists.criarPlaylist(nome);
		return this.conf.getStringList(STRATEGIES_KEY);
	}

	public String indicaEstrategia(String estrategia, int numVideos) {
		StrategyFactory factory = StrategyFactory.getInstance();
		Strategy strat = factory.getStrategy(estrategia);
		List<Video> videos = strat.getVideos(numVideos);
		for(Video v: videos) {
			if(v instanceof Clip) {
				this.playlistCorrente.adicionaClip((Clip) v);
			}
			if(v instanceof Stream) {
				this.playlistCorrente.adicionaStream((Stream) v,
						Duration.ofMinutes(STREAM_MINUTES));
			}
		}
		this.catPlaylists.adicionarPlaylist(this.playlistCorrente);
		return this.playlistCorrente.getCodigo();
	}
}