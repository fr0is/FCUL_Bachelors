package oochess.app.domain.catalogos;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


import oochess.app.domain.Torneio;

public class CatalogoTorneios {
	private Map<String,Torneio> listaTorneios;
	
	private static CatalogoTorneios INSTANCE;
	
	public static CatalogoTorneios getInstance() {
		if(INSTANCE == null) { //Lazy Loading
			INSTANCE = new CatalogoTorneios();
		}
		return INSTANCE;
		
	}
	
	protected CatalogoTorneios() {
		this.listaTorneios = new HashMap<>();
		this.listaTorneios.put("Torneio Xadrez da CADI",new Torneio("Torneio Xadrez da CADI",LocalDate.of(2020,1,1), LocalDate.of(2021,1,1)));
	}
	public Torneio getTorneio(String nome) {
		return this.listaTorneios.get(nome);
	}
	public void adicionaTorneio(Torneio novoTorneio) {
		this.listaTorneios.put(novoTorneio.getNome(), novoTorneio);
	}
}
