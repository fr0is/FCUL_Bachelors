package facade.services;

import java.util.List;

import business.InscreverAulaHandler;
import facade.exceptions.AppException;

/**
 * Servico que permite inscrever utentes em aulas de acordo com o tipo de inscricao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class InscreverAulaService {

	private InscreverAulaHandler iah;

	/**
	 * Cria o servico que permite inscrever utentes
	 * @param iah - InscreverAulaHandler
	 */
	public InscreverAulaService(InscreverAulaHandler iah) {
		this.iah = iah;
	}

	/**
	 * Inicia a inscricao numa aula e devolve as modalidades que existem
	 * @return Modalidades existentes
	 * @throws AppException 
	 */
	public List<String> inscreverAula() throws AppException {
		return iah.inscreverAula();
	}

	/**
	 * Devolve todas as aulas ativas em que o utente se pode inscrever de acordo com a modalidade escolhida e o tipo de inscricao
	 * @param modalidade - modalidade escolhida
	 * @param tipoInscricao - tipo de inscricao a fazer
	 * @return aulas ativas em que o utente se pode inscrever
	 * @throws AppException caso a modalidade e o tipo de inscricao nao forem validos
	 */
	public List<String> infoInscreverAula(String modalidade, int tipoInscricao) throws AppException {
		return iah.infoInscreverAula(modalidade, tipoInscricao);
	}

	/**
	 * Efetua a inscricao concreta do utente na aula definida por este e devolve o custo
	 * @param desAula - aula em que o utente se vai inscrever
	 * @param nrUtente - o numero de inscricao do utente
	 * @return custo da inscricao de acordo com o tipo que foi definido
	 * @throws AppException caso o tipo de inscricao nao tenha sido indicado, a aula nao seja valida ou utente nao exista
	 */
	public double escolheAula(String desAula, int nrUtente) throws AppException {
		return iah.escolheAula(desAula, nrUtente);
	}

}
