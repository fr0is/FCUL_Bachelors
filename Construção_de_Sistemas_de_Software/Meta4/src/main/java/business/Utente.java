package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Objeto que representa um Utente
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
@Entity
@NamedQuery(name=Utente.FIND_BY_NUMEROINSCRICAO, query="SELECT u FROM Utente u WHERE u.nrInscricao = :" + Utente.UTENTE_INSCRICAO)
@NamedQuery(name=Utente.FIND_BY_NOME, query="SELECT u FROM Utente u WHERE u.nome = :" + Utente.UTENTE_NOME)
@NamedQuery(name=Utente.FIND_BY_NIF, query="SELECT u FROM Utente u WHERE u.nif = :" + Utente.UTENTE_NIF)
@Table(name = "UTENTE")
public class Utente{

	public static final String FIND_BY_NUMEROINSCRICAO = "Utente.findByNrInscricao";
	public static final String UTENTE_INSCRICAO = "nrInscricao";

	public static final String FIND_BY_NOME = "Utente.findByNome";
	public static final String UTENTE_NOME = "nome";

	public static final String FIND_BY_NIF = "Utente.findByNif";
	public static final String UTENTE_NIF = "nif";

	@Column(nullable = false, name = "NOME")
	private String nome;

	@Column(nullable = false, unique = true, name = "NIF")
	private int nif;

	@Id
	@Column(name = "NRINSCRICAO")
	private int nrInscricao;

	Utente(){}
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
