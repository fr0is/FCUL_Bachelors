package pleilist.app.domain.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pleilist.app.domain.Video;
import pleilist.app.domain.catalogos.BibliotecaGeral;

public class RandomVideosStrategy implements Strategy {
	
	private BibliotecaGeral bibGeral = BibliotecaGeral.getInstance();
	private static Random generator = new Random();

	@Override
	public List<Video> getVideos(int numVideos) {
		List<Video> videos = new ArrayList<>();
		List<Video> allVids = this.bibGeral.getAllVideos();
		while(numVideos > 0) {
			//num aleatorio entre 0 e allVids.size() - 1
			int index = RandomVideosStrategy.generator.nextInt(allVids.size());
			videos.add(allVids.get(index));
			numVideos--;
		}
		return videos;
	}
	
}