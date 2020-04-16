package facade.services;

import java.util.List;

import business.AtivarAulaHandler;
import facade.exceptions.AppException;

/**
 * Servico que permite ativar uma aula existente num periodo dado
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AtivarAulaService {

	private AtivarAulaHandler aah;

	/**
	 * Constroi este servico dando o handler necessario
	 * @param aah - AtivarAulaHandler
	 */
	public AtivarAulaService(AtivarAulaHandler aah) {
		this.aah = aah;
	}

	/**
	 * Funcao que inicia a ativacao de uma aula e devolve as instalacoes existentes
	 * @return instalacoes existentes
	 */
	public List<String> ativarAula() {
		return aah.ativarAula();
	}

	/**
	 * Ativa a aula indicada num certo periodo na instalacao indicada e define o numero maximo de alunos para essas sessoes
	 * @param desAula - designacao da aula a ser ativada
	 * @param instalacao - instalacao a atribuir
	 * @param data1 - data de inicio
	 * @param data2 - data de fim
	 * @param nrMaxAlunos - numero maximo de alunos para estas aulas definidas
	 * @throws AppException caso a aula ja se encontra ativa, o par de datas nao define um periodo no futuro,
	 * a instalacao nao existe ou nao eh compativel com a modalidade, a instalacao ja se encontra ocupada nesse periodo
	 * ou a capacidade da instalacao nao eh suficiente
	 */
	public void ativAulaInfo(String desAula, String instalacao, String data1, String data2, int nrMaxAlunos) throws AppException {
		aah.ativAulaInfo(desAula, instalacao, data1, data2, nrMaxAlunos);
	}

}
