package business;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Objeto que representa uma inscricao regular
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
@Entity
@DiscriminatorValue(value = "2")
public class InscricaoRegular extends Inscricao {
	
	InscricaoRegular(){}
	/**
	 * Cria uma nova inscricao regular
	 * @param descricao - descricao desta inscricao
	 */
	public InscricaoRegular(int tipo, String descricao) {
		super(tipo, descricao);
	}

	@Override	
	public double calcularCusto(Aula aula) {
		return aula.getPrecoHora() * ((double)aula.getDuracao() / 60) * aula.nrSessoesPorSemana() * 4;
	}
	@Override
	public Inscricao novaInscricao(Utente u) {
		InscricaoRegular ins = new InscricaoRegular(2,super.getDescricao());
		ins.setUtente(u);
		return ins;
	}

}
