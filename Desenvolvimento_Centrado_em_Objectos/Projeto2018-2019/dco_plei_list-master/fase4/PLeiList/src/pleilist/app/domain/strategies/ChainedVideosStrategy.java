package pleilist.app.domain.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import pleilist.app.domain.Hashtag;
import pleilist.app.domain.Video;
import pleilist.app.domain.catalogos.BibliotecaGeral;

public class ChainedVideosStrategy implements Strategy {

	private BibliotecaGeral bibGeral = BibliotecaGeral.getInstance();
	private static Random generator = new Random();

	@Override
	public List<Video> getVideos(int numVideos) {
		List<Video> videos = new ArrayList<>();
		List<Video> allVids = this.bibGeral.getAllVideos();
		int index = ChainedVideosStrategy.generator.nextInt(allVids.size());
		videos.add(allVids.get(index));//podia simplesmente ter chamado getVideos(1) da RandomVideosStrategy, mas nao quero criar dependencias entre strategies 
		while(numVideos - 1 > 0) { //numVideos - 1 porque o primeiro video ja foi adicionado
			Video lastVid = videos.get(videos.size() - 1);
			List<Video> videosComHtgs = new ArrayList<>();
			Set<Hashtag> hashtags = lastVid.getHashtags();
			Video nextVid;
			for(Hashtag h: hashtags) {
				List<Video> videosComHashtag = this.bibGeral.getVideos(h);
				for(Video vid: videosComHashtag) {
					if(!videosComHtgs.contains(vid)) {
						videosComHtgs.add(vid);
					}
				}
			}
			//nao ha necessidade de verificar se videosComHtgs estah vazia,
			//uma vez que pelo menos irá conter o lastVid. Neste caso a playlist
			//irá conter numVideos vezes o lastVid nas strategies
			int i = ChainedVideosStrategy.generator.nextInt(videosComHtgs.size());
			nextVid = videosComHtgs.get(i);
			videos.add(nextVid);
			numVideos--;
		}
		return videos;
	}
}