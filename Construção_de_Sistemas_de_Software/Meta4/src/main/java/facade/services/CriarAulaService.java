package facade.services;

import java.time.LocalTime;
import java.util.List;

import business.CriarAulaHandler;
import facade.exceptions.AppException;

/**
 * Servico que permite criar uma aula
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CriarAulaService {

	private CriarAulaHandler cah;

	/**
	 * Cria este servico que permite criar aulas dando o handler necessario
	 * @param cah - CriarAulaHandler
	 */
	public CriarAulaService(CriarAulaHandler cah) {
		this.cah = cah;
	}

	/**
	 * Inicia a criacao de uma aula e devolve as Modalidades existentes
	 * @return modalidades existentes
	 * @throws AppException 
	 */
	public List<String> criarAula() throws AppException {
		return cah.criarAula();
	}

	/**
	 * Cria a aula com a informacao fornecida
	 * @param modalidade - modalidade a ser praticada na aula
	 * @param nomeAula - designacao da aula
	 * @param dias - dias da semana em que esta aula ira decorrer
	 * @param horaInicio - hora a que esta aula comeca
	 * @param duracao - duracao da aula
	 * @throws AppException caso a modalidade nao exista, o nome da aula ou a duracao nao seja valido
	 */
	public void criarAulaInfo(String modalidade, String nomeAula, List<String> dias, LocalTime horaInicio, int duracao) throws AppException {
		cah.criarAulaInfo(modalidade, nomeAula, dias, horaInicio, duracao);
	}

}
