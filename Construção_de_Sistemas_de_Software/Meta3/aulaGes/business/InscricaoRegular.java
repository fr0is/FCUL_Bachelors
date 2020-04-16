package business;

/**
 * Objeto que representa uma inscricao regular
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class InscricaoRegular extends Inscricao {

	/**
	 * Cria uma nova inscricao regular
	 * @param descricao - descricao desta inscricao
	 */
	public InscricaoRegular(String descricao) {
		super(descricao);
	}

	@Override	
	public double calcularCusto(Aula aula) {
		return aula.getPrecoHora() * ((double)aula.getDuracao() / 60) * aula.nrSessoesPorSemana() * 4;
	}

}
