package pleilist.app.domain;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import pleilist.app.facade.dto.Entrada;

public class PlaylistDomain implements Observer{

	public static final Double PONTUACAO_INICIAL = 0.0;//a pontuacao inicial de uma playlist quando eh criada

	private String nome;
	private String codigo;
	private double pontuacao;

	private List<Item> itens;
	private Set<Hashtag> hashtags;
	private Duration durTotal;


	public PlaylistDomain(String nome, String codigo) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.pontuacao = PONTUACAO_INICIAL;
		this.itens = new ArrayList<>();//UC2-1f-1.2.1
		this.hashtags = new HashSet<>();//UC2-1f-1.2.2
		this.durTotal = Duration.ZERO;
	}

	public String getNome() {
		return this.nome;
	}
	public String getCodigo() {
		return this.codigo;
	}
	
	public double getPontuacao() {
		return this.pontuacao;
	}

	public Set<Hashtag> getHashtags() {
		return this.hashtags;
	}

	public List<Item> getItens(){
		return this.itens;
	}

	public Duration getDurTotal(){
		return this.durTotal;
	}

	public List<Entrada> getEntradas() {
		List<Entrada> entradas = new ArrayList<>();//UC2-1v-1.1
		for(Item item: this.itens) {//UC2-1v-1.2
			Entrada entrada = new Entrada(item.getVideo(),
					item.getDuracaoTrans(), item.getHoraInicio());//UC2-1v-1.3
			entradas.add(entrada);//UC2-1v-1.4
		}
		return entradas;
	}

	public void adicionaClip(Clip clip) {
		Item item = new Item(clip);//UC2-2v-4.1 e UC2-2v-4.4
		Duration dur = clip.getDuracao();//UC2-2v-4.2
		item.setDurTransmissao(dur);//UC2-2v-4.3
		item.setHoraInicio(this.durTotal);//UC2-2v-4.5
		this.itens.add(item);//UC2-2v-4.6
		this.atualizaDurTotal(dur);//UC2-2v-4.7
		Set<Hashtag> hashtags = clip.getHashtags();//UC2-2v-4.8
		for(Hashtag h: hashtags) {
			this.hashtags.add(h);//UC2-2v-4.9
		}
		clip.addObserver(this);
	}

	private void atualizaDurTotal(Duration dur) {
		this.durTotal = this.durTotal.plus(dur);
	}

	public void adicionaStream(Stream stream, Duration duration) {
		
		Item item = new Item(stream);//UC2-3f-1.1 e UC2-3f-1.3
		item.setDurTransmissao(duration);//UC2-3f-1.2
		item.setHoraInicio(this.durTotal);//UC2-3f-1.4
		this.itens.add(item);//UC2-3f-1.5
		this.atualizaDurTotal(duration);//UC2-3f-1.6
		Set<Hashtag> hashtags = stream.getHashtags();//UC2-3f-1.7
		for(Hashtag h: hashtags) {
			this.hashtags.add(h);//UC2-3f-1.8
		}
		stream.addObserver(this);
	}

	public Item getProximoItem(int indiceAtual) {
		return this.itens.get(indiceAtual);//UC4-2f-1.1
	}

	public void atualizaClassMedia() {
		//UC2-2v-2.1 jah nao vamos fazer assim
		double soma = 0.0;
		int nItens = this.itens.size();//UC2-2v-2.2
		for(Item item: this.itens) {
			Video video = item.getVideo();
			soma += video.getClassificacaoMedia().getEstrelas();
		}
		this.pontuacao = soma / nItens;
		//UC2-2v-2.3 jah nao vamos fazer assim
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Video) {
			this.atualizaClassMedia();
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("--------------------------------------------\n");
		String plNome = this.getNome();
		String plCodigo = this.getCodigo();
		sb.append("Nome playlist: " + plNome + "\n"
				+ "Codigo: " + plCodigo);
		this.getItens().stream().forEach((Item item) -> {
			Video itemVideo = item.getVideo();
			sb.append("\n	Video: " +
					"\n		Titulo: " + itemVideo.getTitulo() +
					"\n		Codigo: " + itemVideo.getCodigo() +
					"\n		Classificacao Media: " + itemVideo.getClassificacaoMedia().getEstrelas()
					);
		});
		sb.append("\n--------------------------------------------");
		return sb.toString();
	}
	
}
