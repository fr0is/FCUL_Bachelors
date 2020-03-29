package pleilist.app.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Set;

public class Video extends Observable{
	
	private Set<Hashtag> hashtags;
	private Map<Utilizador, Classificacao> classificacoes;
	private String title, address;
	private boolean ePublico;//true se for publico
	private String codigo;
	private Classificacao clMedia;
	private int views;

	protected Video() {
		this.hashtags = new HashSet<Hashtag>();//UC1-1v-1.1a.1 ou //UC1-1v-1.1b.1
		this.classificacoes = new HashMap<Utilizador, Classificacao>();//UC1-1v-1.1a.2 ou //UC1-1v-1.1b.2
		this.clMedia = new Classificacao(0);
		this.views = 0;
		//outros atributos serao inicializados por metodos
	}
	
	/**
	 * Define o titulo e endereco deste video
	 * @param title - titulo deste video
	 * @param address - endereco deste video
	 * @requires title != null && address != null
	 */
	public void defTitleAndAddress(String title, String address) {
		this.title = title;
		this.address = address;
	}

	public void adicionarHashtag(Hashtag h) {
		this.hashtags.add(h);//UC1-3f-2.1
	}

	public void indicaPrivacidade(boolean ePublico) {
		this.ePublico = ePublico;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Set<Hashtag> getHashtags() {
		return this.hashtags;
	}
	
	public String getTitulo() {
		return this.title;
	}
	
	public String getEndereco() {
		return this.address;
	}
	
	public String getCodigo() {
		return this.codigo;
	}

	public boolean ehStream() {
		return this instanceof Stream;
	}
	
	public int getVisualizacoes() {
		return this.views;
	}
	
	public Classificacao getClassificacaoMedia() {
		return this.clMedia;
	}
	
	public void classifica(Utilizador ut, int estrelas) {
		Classificacao newCl = new Classificacao(estrelas);//UC4-2v-1.1
		//UC4-2v-1.2 jah nao vamos fazer assim
		this.classificacoes.put(ut, newCl);//UC4-2v-1.3
		Collection<Classificacao> classificacoes = this.classificacoes.values();//UC4-2v-1.4
		this.atualizaClassMedia(classificacoes);//UC4-2v-1.5
	}


	private void atualizaClassMedia(Collection<Classificacao> classificacoes) {
		int nClassificacoes = classificacoes.size();
		double soma = 0.0;
		for(Classificacao cl: classificacoes) {
			soma += cl.getEstrelas();
		}
		double estrelasMedias = soma / nClassificacoes;
		this.clMedia = new Classificacao(estrelasMedias);
		this.setChanged();
		this.notifyObservers();
	}

	public void atualizaVisualizacoes() {
		this.views++;
	}

}
