package business;

/**
 * Objeto que representa uma modalidade
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class Modalidade {

	private String nome;
	private int duracaoMin;
	private double precoPorHora;
	private TipoInstalacao instalacaoCompativel;

	/**
	 * Cria uma nova modalidade
	 * @param nome - nome desta modalidade
	 * @param duracaoMin - duracao minima que esta modalidade tem de ser praticada nas aulas(em minutos)
	 * @param instalacaoCompativel - tipo de instalacao compativel com esta modalidade
	 * @param precoPorHora - preco por hora desta modalidade
	 */
	public Modalidade(String nome, int duracaoMin,TipoInstalacao instalacaoCompativel, double precoPorHora) {
		this.nome = nome;
		this.duracaoMin = duracaoMin;
		this.precoPorHora = precoPorHora;
		this.instalacaoCompativel = instalacaoCompativel;
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
	 * Devolve o tipo de instalacao compativel
	 * @return o tipo de instalacao compativel
	 */
	public TipoInstalacao getInstalacaoCompativel() {
		return this.instalacaoCompativel;
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
