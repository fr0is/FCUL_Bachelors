package pleilist.app.domain.catalogos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import pleilist.app.domain.Clip;
import pleilist.app.domain.Hashtag;
import pleilist.app.domain.Stream;
import pleilist.app.domain.Video;

public class Biblioteca {

	private int nextCodigo = 0;

	protected Map<String, Video> videos;
	protected Map<Hashtag, List<Video>> videosPorHstg;

	protected Biblioteca() {
		this.videos = new HashMap<>();
		this.videosPorHstg = new HashMap<>();
	}

	private static Biblioteca INSTANCE = null;

	public static Biblioteca getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new Biblioteca();
		}
		return INSTANCE;
	}

	public Video criarVideo(boolean isClip){
		if(isClip) {
			return new Clip();//UC1-1v-1.1a
		}else {
			return new Stream();//UC1-1v-1.1b
		}
	}

	public String novoCodigo() {
		String cod = "vid_" + Integer.toString(this.nextCodigo);
		this.nextCodigo++;
		return cod;
	}

	public void adicionaVideo(Video vidCorrente, String codigo) {
		vidCorrente.setCodigo(codigo);//UC1-3v-3a.1.1 ou UC1-3v-3b.1
		this.videos.put(codigo, vidCorrente);//UC1-3v-3a.1.2 ou UC1-3v-3b.2
		Set<Hashtag> hashtags = vidCorrente.getHashtags();//UC1-3v-3a.1.3 ou UC1-3v-3b.3
		Iterator<Hashtag> it = hashtags.iterator();
		while(it.hasNext()) {
			Hashtag h = it.next();
			Optional<List<Video>> talvezVideosComHtg = Optional.ofNullable(this.videosPorHstg.get(h));//UC1-3v-3a.1.4 ou UC1-3v-3b.4
			List<Video> videosComHtg = talvezVideosComHtg.orElse(new ArrayList<>());
			videosComHtg.add(vidCorrente);//UC1-3v-3a.1.5 ou UC1-3v-3b.5
			this.videosPorHstg.put(h, videosComHtg);//UC1-3v-3a.1.6 ou UC1-3v-3b.6
		}
	}

	public List<Video> getVideos(Hashtag hashtag){
		//UC2-2f-2.1 ou UC2-2f-3.1.1 passou a ser feito no handler para que este metodo pudesse ser usado tambem na ChainedVideosStrategy
		Optional<List<Video>> talvezVideosComHashtag = Optional.ofNullable(this.videosPorHstg.get(hashtag));//UC2-2f-2.2 ou UC2-2f-3.1.2
		//UC2-2f-2.3 ou UC2-2f-3.1.3 passou a ser feito no handler para que este metodo pudesse ser usado tambem na ChainedVideosStrategy
		//UC2-2f-2.4 ou UC2-2f-3.1.4 passou a ser feito no handler para que este metodo pudesse ser usado tambem na ChainedVideosStrategy
		//UC2-2f-2.5 ou UC2-2f-3.1.5 passou a ser feito no handler para que este metodo pudesse ser usado tambem na ChainedVideosStrategy
		return talvezVideosComHashtag.orElse(new ArrayList<>());
	}
	
	public Optional<Video> getVideo(String codigo){
		Optional<Video> talvezVideo = Optional.ofNullable(this.videos.get(codigo));//UC2-2v-1.1.1 ou UC2-2v-2.1
		if(talvezVideo.isPresent()) {
			return talvezVideo;
		}
		return Optional.empty();
	}
}
