package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Objeto que representa uma modalidade
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
@Entity
@NamedQuery(name=Modalidade.FIND_BY_ID, query="SELECT m FROM Modalidade m WHERE m.id = :" + Modalidade.MODALIDADE_ID)
@NamedQuery(name=Modalidade.FIND_BY_NOME, query="SELECT m FROM Modalidade m WHERE m.nome = :" + Modalidade.MODALIDADE_NOME)
@NamedQuery(name=Modalidade.MODALIDADE_GET_NOMES, query="SELECT m.nome FROM Modalidade m")
@Table(name = "MODALIDADE")
public class Modalidade{

	public static final String FIND_BY_ID = "Modalidade.findById";
	public static final String MODALIDADE_ID = "id";

	public static final String FIND_BY_NOME = "Modalidade.findByNome";
	public static final String MODALIDADE_NOME = "nome";

	public static final String MODALIDADE_GET_NOMES = "Modalidade.getAllNomes";

	@Id
	@Column(name = "MODALIDADE_ID")
	private int id;

	@Column(nullable = false, name = "NOME")
	private String nome;

	@Column(nullable = false, name = "DURACAOMIN")
	private int duracaoMin;

	@Column(nullable = false, name = "PRECOPORHORA")
	private double precoPorHora;

	Modalidade(){}
	/**
	 * Cria uma nova modalidade
	 * @param nome - nome desta modalidade
	 * @param duracaoMin - duracao minima que esta modalidade tem de ser praticada nas aulas(em minutos)
	 * @param instalacaoCompativel - tipo de instalacao compativel com esta modalidade
	 * @param precoPorHora - preco por hora desta modalidade
	 */
	public Modalidade(String nome, int duracaoMin, double precoPorHora) {
		this.nome = nome;
		this.duracaoMin = duracaoMin;
		this.precoPorHora = precoPorHora;
	}

	/**
	 * Devolve a duracao minima desta modalidade
	 * @return a duracao minima desta modalidade
	 */
	public int getDuracaoM() {
		return this.duracaoMin;
	}

	/**
	 * Devolve o preco por hora desta modalidade
	 * @return o preco por hora desta modalidade
	 */
	public double getPrecoPorHora() {
		return this.precoPorHora;
	}

	/**
	 * Devolve o nome desta modalidade
	 * @return o nome desta modalidade
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Verifica se a duracao de uma aula eh superior a minima desta modalidade
	 * @param duracao - duracao da aula
	 * @return true se a duracao da aula for superior ou igual a minima desta modalidade
	 */
	public boolean duracaoValida(int duracao) {
		return duracao >= this.duracaoMin;
	}

}
