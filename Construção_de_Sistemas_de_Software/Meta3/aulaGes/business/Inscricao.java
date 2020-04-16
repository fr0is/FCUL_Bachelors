package business;

/**
 * Objeto que representa uma inscricao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public abstract class Inscricao {

	private String descricao;
	private Utente utente;

	/**
	 * Cria uma nova inscricao
	 * @param descricao - descricao desta inscricao
	 */
	public Inscricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Associa um utente a esta inscricao
	 * @param u - utente a associar
	 */
	public void setUtente(Utente u) {
		this.utente = u;
	}

	/**
	 * Devolve o nome do utente associado
	 * @return o nome do utente associado
	 */
	public String getUtenteNome() {
		return this.utente.getNome();
	}

	/**
	 * Devolve o numero de inscricao do utente associado
	 * @return o numero de inscricao do utente associado
	 */
	public int getUtenteNrInscricao() {
		return this.utente.getNrInscricao();
	}

	/**
	 * Devolve o nif do utente associado
	 * @return o nif do utente associado
	 */
	public int getUtenteNIF() {
		return this.utente.getNif();
	}

	/**
	 * Devolve a descricao desta inscricao
	 * @return a descricao desta inscricao
	 */
	public String getDescricao(){
		return this.descricao;
	}

	/**
	 * Calcula o custo da inscricao para uma aula
	 * @param aula - aula em que o utente se inscreveu
	 * @return custo da inscricao
	 */
	public abstract double calcularCusto(Aula aula); 

}
