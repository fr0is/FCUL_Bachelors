package business;

import java.util.List;

import facade.exceptions.AppException;

/**
 * Handler que permite criar uma aula
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CriarAulaHandler {

	private CatalogoModalidades cm;
	private CatalogoAulas ca;

	/**
	 * Cria o handler para criar aulas dando os catalogos necessarios
	 * @param cm - Catalogo de modalidades
	 * @param ca - Catalogo de Aulas
	 */
	public CriarAulaHandler(CatalogoModalidades cm, CatalogoAulas ca) {
		this.cm = cm;
		this.ca = ca;
	}

	/**
	 * Inicia a criacao de uma aula e devolve as Modalidades existentes
	 * @return modalidades existentes
	 */
	public List<String> criarAula() {
		return this.cm.getModalidades();
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
	public void criarAulaInfo(String modalidade, String nomeAula, List<String> dias, String horaInicio, int duracao) throws AppException {
		if(!this.cm.contem(modalidade)) {
			throw new AppException("Modalidade nao existente");
		}
		if(!((nomeAula.length() == 6) || !temAlfaNumericos(nomeAula) || nomeAula.contains(" ")) ||
				ca.existeAula(nomeAula)) {
			throw new AppException("Nome da Aula nao eh valido");
		}
		/*
		 * Sabemos que a duracao tem de ser positiva (maior que 0),
		 * e que tem de ser superior a duracao minima de cada modalidade.
		 * Se essa duracao ja eh positiva decidimos comparar a duracao dada para esta funcao
		 * apenas com a duracao minima da modalidade, nao sendo necessario ver se eh maior
		 * do que zero
		 */
		if(!this.cm.duracaoValida(modalidade, duracao)) {
			throw new AppException("Duracao nao eh valida");
		}
		this.ca.criarAula(this.cm.getModalidade(modalidade), nomeAula, dias, horaInicio, duracao);
	}

	/**
	 * Funcao auxiliar que verifica se o nome da aula eh valido
	 * @param nomeAula - nome a analisar
	 * @return true caso o nome seja valido
	 */
	private boolean temAlfaNumericos(String nomeAula) {
		int ocorrencias = 0;
		for(int i = 0; i < nomeAula.length() && ocorrencias < 3; i++) {
			char c = nomeAula.charAt(i);
			if(Character.isDigit(c) && Character.isLetter(c)) {
				ocorrencias++;
			}	
		}
		return (ocorrencias >= 3);
	}

}
