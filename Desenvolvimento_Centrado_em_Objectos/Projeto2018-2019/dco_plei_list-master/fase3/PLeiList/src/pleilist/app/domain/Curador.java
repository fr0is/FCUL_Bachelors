package pleilist.app.domain;

import java.util.List;
import java.util.Optional;

import pleilist.app.domain.catalogos.BibliotecaPessoal;

public class Curador extends Utilizador{

	private BibliotecaPessoal bibPessoal;

	public Curador(String nome, String password) {
		super(nome, password);
		this.bibPessoal = new BibliotecaPessoal();
	}

	public void adicionaVideo(Video vidCorrente, String codigo) {
		this.bibPessoal.adicionaVideo(vidCorrente, codigo);//UC1-3v-3a.1
	}

	public List<Video> getVideos(Hashtag hashtag) {//agora devolve lista de videos em vez de lista de pares de titulo e codigo, porque quero usar o getVideos para me devolver os videos na ChainedVideosStrategy
		return this.bibPessoal.getVideos(hashtag);//UC2-2f-3.1
	}

	public Optional<Video> getVideo(String codigo) {
		return this.bibPessoal.getVideo(codigo);//UC2-2v-1.1
	}

}
