package pleilist.app.facade.dto;

import pleilist.app.domain.Item;
import pleilist.app.domain.Video;

public class Playlist {
	private String nome;
	private String codigo;
	private double pontuacao;

	public Playlist(String nome, String codigo, double pontuacao) {
		super();
		this.nome = nome;
		this.codigo = codigo;
		this.pontuacao = pontuacao;
	}


	public String getNome() {
		return nome;
	}
	public String getCodigo() {
		return codigo;
	}
	public double getPontuacao() {
		return pontuacao;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder("--------------------------------------------\n");
		String plNome = this.getNome();
		String plCodigo = this.getCodigo();
		sb.append("Nome playlist: " + plNome + "\n"
				+ "Codigo: " + plCodigo);
		
		sb.append("\n--------------------------------------------");
		return sb.toString();
	}
}
