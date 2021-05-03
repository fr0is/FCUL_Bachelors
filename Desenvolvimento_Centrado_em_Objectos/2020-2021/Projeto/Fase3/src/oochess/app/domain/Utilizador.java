package oochess.app.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Utilizador {
	private String username;
	private String discordID;
	private String password;
	private Map<String,Desafio> listaDesafios;
	private double elo;

	public Utilizador(String username,String password,String discordID,double elo) {
		this.username = username;
		this.discordID = discordID;
		this.password = password;
		this.elo = elo;
		this.listaDesafios = new HashMap<>();
	}

	public void adicionaDesafio(String cod,Desafio d) {
		this.listaDesafios.put(cod,d);
	}

	public String getUsername() {
		return username;
	}

	public String getDiscordID() {
		return discordID;
	}

	public double getElo() {
		return elo;
	}

	public void addElo(double elo) {
		if(this.elo + elo < 0) {
			this.elo = 0;
		}else {
			this.elo = this.elo + elo;
		}
	}

	public boolean autentica(String password) {
		return this.password.equals(password);
	}

	/**
	 * UC6-1.1.1
	 * Obter desafio associado ao codigoDesafio dado
	 * @param codigoDesafio
	 * @return
	 */
	public Optional<Desafio> getDesafio(String codigoDesafio) {
		if(this.listaDesafios.containsKey(codigoDesafio)) {
			return Optional.of(this.listaDesafios.get(codigoDesafio));
		}
		return Optional.empty();	
	}

	public Desafio getDesafioExistente(String codigoDesafio) {
		return this.listaDesafios.get(codigoDesafio);	
	}
	/**
	 * Metodo que vai recolher os desafios pendentes de um jogador e guarda-os numa List
	 * @return devolve List com os desafios Pendentes do Jogador
	 */
	public List<Desafio> getDesafiosPendentes() {
		List<Desafio> desafiosPendentes = new ArrayList<>();
		for(Desafio d: this.listaDesafios.values()) {
			if(d.getEstado().equals(Estado.PENDENTE)){
				desafiosPendentes.add(d);
			}
		}
		return ordenarPorMaisProximoDeAcontecer(desafiosPendentes);
	}
	/**
	 * Ordena lista de desafios pendentes por ordem de qual dos desafios vai decorrer mais cedo/proximo
	 * @param desafiosPendentes lista de desafios pendentes
	 * @return lista ordenada de  desafios pendentes
	 */
	private List<Desafio> ordenarPorMaisProximoDeAcontecer(List<Desafio> desafiosPendentes) {
		List<Desafio> l = desafiosPendentes;
		Comparator<Desafio> byOrderTime = (d1, d2) -> {
			if (d1.getData().isBefore(d2.getData())) return -1; 
			else return 1;
		};
		Collections.sort(l, byOrderTime);
		return l;
	}
	
	public void aceitaDesafio(String codigo) {
		Desafio d = this.listaDesafios.get(codigo);
		d.atualizaEstado(Estado.ACEITE);
		this.listaDesafios.replace(codigo, d);
	}

	public void rejeitaDesafio(String codigo) {
		Desafio d = this.listaDesafios.get(codigo);
		d.atualizaEstado(Estado.RECUSADO);
		this.listaDesafios.replace(codigo, d);
	}

	public void replaceDesafio(String codigo, Desafio dNovo) {
		this.listaDesafios.replace(codigo, dNovo);
	}

}
