package pleilist.app.domain.strategies;

import java.util.List;

import pleilist.app.domain.Video;

public interface Strategy {

	public List<Video> getVideos(int numVideos);
	
}
