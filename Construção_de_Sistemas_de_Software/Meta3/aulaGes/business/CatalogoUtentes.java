package business;

import java.util.HashMap;
import java.util.Map;

/**
 * Catalogo que guarda os utentes no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoUtentes {

	private Map<Integer,Utente> utentes;

	/**
	 * Inicia o catalogo
	 */
	public CatalogoUtentes() {
		this.utentes = new HashMap<>();
		this.loadData();
	}

	/**
	 * Carrega os dados do Catalogo
	 */
	private void loadData() {
		utentes.put(1, new Utente("Alexandre",124683756,1));
		utentes.put(2, new Utente("Antonio",324568425,2));
		utentes.put(3, new Utente("Filipe",159645155,3));
		utentes.put(4, new Utente("Mena",124556734,4));
		utentes.put(5, new Utente("Thanos",784565918,5));
	}

	/**
	 * Devolve o utente correspondente ao nrUtente
	 * @param nrUtente - numero de inscricao do utente
	 * @return o utente ou null caso o utente indicado nao exista
	 */
	public Utente getUtente(int nrUtente) {
		return this.utentes.get(nrUtente);
	}

}
