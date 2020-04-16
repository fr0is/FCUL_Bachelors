package business;

import java.time.LocalDate;
import java.util.List;

import facade.exceptions.AppException;

/**
 * Handler que permite ver a ocupacao de uma instalacao num dado dia
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class VisualizarOcupacaoHandler {

	private CatalogoInstalacoes ci;

	/**
	 * Cria o handler que permite ver a ocupacao de uma instalacao dando o catalogo necessario
	 * @param ci - Catalogo de instalacoes
	 */
	public VisualizarOcupacaoHandler(CatalogoInstalacoes ci) {
		this.ci = ci;
	}

	/**
	 * Visualiza a ocupacao da instalacao no dia indicado
	 * @param instalacao - instalacao a visualizar
	 * @param data - data que se pretende ver a ocupacao
	 * @return lista com todas as sessoes que irao decorrer nesta instalacao
	 * @throws AppException caso a instalacao nao seja valida
	 */
	public List<String> visualizarOcupacao(String instalacao, String data) throws AppException{
		if(!ci.existeInstalacao(instalacao))
			throw new AppException("A instalacao nao existe");
		return this.ci.visualizarOcupacao(instalacao,this.convert(data));
	}

	/**
	 * Funcao que converte a data de String para um objeto do tipo LocalDate
	 * @param d - data a ser convertida
	 * @return data como objeto LocalDate
	 * @throws AppException caso o formato seja invalido
	 */
	private LocalDate convert(String d) throws AppException {
		int ocorrencias = 0;
		for(int i = 0; i < d.length(); i++) {
			if(d.charAt(i) == '-') {
				ocorrencias++;
			}	
		}
		if(ocorrencias != 2) {
			throw new AppException("Formato da data invalido, formato valido: yyyy-MM-dd");
		}
		String[] dataInfo = d.split("-");
		try {
			return LocalDate.of(Integer.parseInt(dataInfo[0]), Integer.parseInt(dataInfo[1]), Integer.parseInt(dataInfo[2]));
		}catch(NumberFormatException e) {
			throw new AppException("Formato da data invalido, formato valido: yyyy-MM-dd");
		}
	}

}
