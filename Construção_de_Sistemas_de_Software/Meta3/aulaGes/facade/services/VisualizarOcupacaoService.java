package facade.services;

import java.util.List;

import business.VisualizarOcupacaoHandler;
import facade.exceptions.AppException;

/**
 * Servico que permite visualizar a ocupacao de uma instalacao num dado dia
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class VisualizarOcupacaoService {

	private VisualizarOcupacaoHandler voh;

	/**
	 * Cria o handler que permite ver a ocupacao de uma instalacao dando o handler necessario
	 * @param voh - VisualizarOcupacaoHandler
	 */
	public VisualizarOcupacaoService(VisualizarOcupacaoHandler voh) {
		this.voh = voh;
	}

	/**
	 * Visualiza a ocupacao da instalacao no dia indicado
	 * @param nomeInstalacao - instalacao a visualizar
	 * @param data - data que se pretende ver a ocupacao
	 * @return lista com todas as sessoes que irao decorrer nesta instalacao
	 * @throws AppException caso a instalacao nao seja valida
	 */
	public List<String> visualizarOcupacao(String nomeInstalacao, String data) throws AppException{
		return voh.visualizarOcupacao(nomeInstalacao, data);
	}

}
