package business;

/**
 * Objeto que representa um Utente
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class Utente {

	private String nome;
	private int nif;
	private int nrInscricao;

	/**
	 * Cria um novo utente
	 * @param nome - nome do utente
	 * @param nif - nif do utente
	 * @param nrInscricao - numero de inscricao do utente
	 */
	public Utente(String nome, int nif, int nrInscricao) {
		this.nome = nome;
		this.nif = nif;
		this.nrInscricao = nrInscricao;
	}

	/**
	 * Devolve o nome do utente
	 * @return o nome do utente
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Devolve o nif do utente
	 * @return o nif do utente
	 */
	public int getNif() {
		return nif;
	}

	/**
	 * Devolve o numero de inscricao do utente
	 * @return o numero de inscricao do utente
	 */
	public int getNrInscricao() {
		return nrInscricao;
	}

}
