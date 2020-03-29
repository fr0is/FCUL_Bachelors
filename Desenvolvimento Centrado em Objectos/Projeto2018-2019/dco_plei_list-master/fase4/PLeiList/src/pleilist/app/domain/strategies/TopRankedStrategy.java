package pleilist.app.domain.strategies;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pleilist.app.domain.Video;
import pleilist.app.domain.catalogos.BibliotecaGeral;

public class TopRankedStrategy implements Strategy {

	private BibliotecaGeral bibGeral = BibliotecaGeral.getInstance();
	private static Comparator<Video> classifComparator = new Comparator<Video>() {
		@Override
		public int compare(Video vid1, Video vid2) {
			double estrelas1 = vid1.getClassificacaoMedia().getEstrelas();
			double estrelas2 = vid2.getClassificacaoMedia().getEstrelas();
			if(estrelas1 > estrelas2) {
				return 1;
			}
			if(estrelas1 < estrelas2) {
				return -1;
			}
			return 0;
		}
	};

	@Override
	public List<Video> getVideos(int numVideos) {
		List<Video> videos = new ArrayList<>();
		List<Video> allVids= this.bibGeral.getAllVideos();
		Object[] vidsOrdPorEstrelas = allVids.stream()
				.sorted(classifComparator)
				.toArray();
		for(int i = 0; i < numVideos; i++) {
			int lastPosition = vidsOrdPorEstrelas.length - 1;
			Object obj = vidsOrdPorEstrelas[lastPosition - i];//lastPosition - i porque queremos os mais votados que estao no fim do array, visto que este estah ordenado por ordem crescente
			if(obj instanceof Video) {
				Video vid = (Video) obj;
				videos.add(vid);
			}
		}
		return videos;
	}

}