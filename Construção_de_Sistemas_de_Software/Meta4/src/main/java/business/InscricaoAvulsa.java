package business;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Objeto que representa uma inscricao avulsa
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
@Entity
@DiscriminatorValue(value = "1")
public class InscricaoAvulsa extends Inscricao{
	
	InscricaoAvulsa(){}
	/**
	 * Cria uma nova inscricao avulsa
	 * @param i 
	 * @param descricao - descricao desta inscricao
	 */
	public InscricaoAvulsa(int tipo, String descricao) {
		super(tipo, descricao);
	}

	@Override
	public double calcularCusto(Aula aula) {
		return aula.getPrecoHora() * ((double)aula.getDuracao() / 60);
	}
	@Override
	public Inscricao novaInscricao(Utente u) {
		InscricaoAvulsa ins = new InscricaoAvulsa(1,super.getDescricao());
		ins.setUtente(u);
		return ins;
	}

}
