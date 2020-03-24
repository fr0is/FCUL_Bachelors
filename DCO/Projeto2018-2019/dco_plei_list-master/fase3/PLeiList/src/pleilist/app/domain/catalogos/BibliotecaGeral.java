package pleilist.app.domain.catalogos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import pleilist.app.domain.Video;

public class BibliotecaGeral extends Biblioteca{
	
	private static Comparator<Video> viewsComparator = new Comparator<Video>() {
		@Override
		public int compare(Video vid1, Video vid2) {
			double views1 = vid1.getVisualizacoes();
			double views2 = vid2.getVisualizacoes();
			if(views1 > views2) {
				return 1;
			}
			if(views1 < views2) {
				return -1;
			}
			return 0;
		}
	};

	private BibliotecaGeral() {
		super();
	}

	private static BibliotecaGeral INSTANCE = null;

	public static BibliotecaGeral getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new BibliotecaGeral();
		}
		return INSTANCE;
	}

	public List<Video> getTop10Videos() {
		List<Video> top10 = new ArrayList<>();
		Object[] allVidsSorted = this.getAllVideos().stream()
				.sorted(viewsComparator)
				.toArray();
		for(int i = 0; i < 10 && i < allVidsSorted.length; i++) {
			int lastPosition = allVidsSorted.length - 1;
			Object obj = allVidsSorted[lastPosition - i];//lastPosition - i porque queremos os mais vistos que estao no fim do array, visto que este estah ordenado por ordem crescente
			if(obj instanceof Video) {
				Video vid = (Video) obj;
				top10.add(vid);
			}
		}
		return top10;
	}

	public List<Video> getAllVideos(){
		Collection<Video> collection = this.videos.values();
		List<Video> result = new ArrayList<>();
		for(Video v: collection) {
			result.add(v);
		}
		return result;
	}
}
