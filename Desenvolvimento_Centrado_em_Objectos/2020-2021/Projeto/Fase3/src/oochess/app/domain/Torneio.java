package oochess.app.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Torneio {
	private String nome;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private Map<String,Inscricao> listaInscricoes; //??? para q e map?
	
	public Torneio(String nome,LocalDate dataInicio,LocalDate dataFim) {
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.listaInscricoes = new HashMap<>();
	}
	
	public String getNome() {
		return nome;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public Map<String, Inscricao> getListaInscricoes() {
		return listaInscricoes;
	}

}
