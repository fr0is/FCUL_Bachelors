package business;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.DiscriminatorType.INTEGER;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import javax.persistence.JoinColumn;

/**
 * Objeto que representa uma inscricao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
@Entity
@NamedQuery(name=Inscricao.FIND_BY_ID, query="SELECT i FROM Inscricao i WHERE i.id = :" + Inscricao.INSCRICAO_ID)
@NamedQuery(name=Inscricao.FIND_BY_TIPO, query="SELECT i FROM Inscricao i WHERE i.tipo = :" + Inscricao.INSCRICAO_TIPO)
@Table(name = "INSCRICAO")
@DiscriminatorColumn(discriminatorType = INTEGER, name = "TIPO")
@Inheritance(strategy = SINGLE_TABLE)
public abstract class Inscricao{

	public static final String FIND_BY_ID = "Inscricao.findById";
	public static final String INSCRICAO_ID = "id";

	public static final String FIND_BY_TIPO = "Inscricao.findByTipo";
	public static final String INSCRICAO_TIPO = "tipo";


	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "INSCRICAO_ID")
	private int id;

	@Column(nullable = false, name = "TIPO")
	private int tipo;

	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

	@ManyToOne(cascade = ALL, optional = true)
	@JoinColumn(name = "UTENTE_NRINSCRICAO", referencedColumnName = "NRINSCRICAO")
	private Utente utente;

	Inscricao(){}
	/**
	 * Cria uma nova inscricao
	 * @param id 
	 * @param descricao - descricao desta inscricao
	 */
	public Inscricao(int tipo, String descricao) {
		this.tipo = tipo;
		this.descricao = descricao;
	}

	/**
	 * Devolve o id desta inscricao
	 * @return
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Devolve o tipo deste tipo de inscricao
	 * @return
	 */
	public int getTipo() {
		return this.tipo;
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

	/**
	 * Devolve um novo objeto do tipo inscricao com um utente associado
	 * @param u - Utente a associar
	 * @return nova inscricao
	 */
	public abstract Inscricao novaInscricao(Utente u);
}
