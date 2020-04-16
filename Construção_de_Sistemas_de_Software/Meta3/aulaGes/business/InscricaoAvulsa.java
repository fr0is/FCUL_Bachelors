package business;

/**
 * Objeto que representa uma inscricao avulsa
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class InscricaoAvulsa extends Inscricao {

	/**
	 * Cria uma nova inscricao avulsa
	 * @param descricao - descricao desta inscricao
	 */
	public InscricaoAvulsa(String descricao) {
		super(descricao);
	}

	@Override
	public double calcularCusto(Aula aula) {
		return aula.getPrecoHora() * ((double)aula.getDuracao() / 60);
	}

}
