package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Catalogo que guarda as Instalacoes existentes no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoInstalacoes {

	private Map<String, Instalacao> instalacoes;

	/**
	 * Inicia o catalogo
	 */
	public CatalogoInstalacoes() {
		this.instalacoes = new HashMap<>();
		this.loadData();
	}

	/**
	 * Carrega os dados do catalogo
	 */
	private void loadData() {
		instalacoes.put("PistaDeBicicletas", new Instalacao("PistaDeBicicletas",50,TipoInstalacao.SALABICICLETAS));
		instalacoes.put("SalaMultimedia", new Instalacao("SalaMultimedia",20,TipoInstalacao.ESTUDIO));
		instalacoes.put("piscina", new Instalacao("piscina",40,TipoInstalacao.PISCINA));
		instalacoes.put("CampoDeTenis", new Instalacao("CampoDeTenis",20,TipoInstalacao.CAMPODETENIS));
		instalacoes.put("Ginasio", new Instalacao("Ginasio",20,TipoInstalacao.ESTUDIO));
	}

	/**
	 * Devolve as instalacoes existentes
	 * @return lista das instalacoes
	 */
	public List<String> getInstalacoes() {
		List<String> inst = new ArrayList<>();
		for(String i: this.instalacoes.keySet())
			inst.add(i);
		return inst;
	}

	/**
	 * Verifica se uma instalacao existe
	 * @param instalacao - instalacao a verificar
	 * @return true se a instalacao existe
	 */
	public boolean existeInstalacao(String instalacao) {
		return this.instalacoes.containsKey(instalacao);
	}

	/**
	 * Verifica se a instalacao eh compativel com uma certa modalidade
	 * @param instalacao - instalacao que se quer ver
	 * @param aulaModalidade - modalidade que se quer ver se eh compativel
	 * @return true se a instalacao for compativel com a modalidade
	 */
	public boolean instalacaoCompativel(String instalacao, Modalidade aulaModalidade) {
		return this.instalacoes.get(instalacao).ehCompativel(aulaModalidade);
	}

	/**
	 * Verifica se a instalacao consegue ter o maximo de alunos de uma aula
	 * @param instalacao - instalacao a verificar
	 * @param nrMaxAlunos - numero maximo de alunos numa aula
	 * @return true se o numero maximo de alunos for inferior ou igual a capacidade da instalacao
	 */
	public boolean capacidadeMaxValida(String instalacao, int nrMaxAlunos) {
		return this.instalacoes.get(instalacao).getCapacidade() >= nrMaxAlunos;
	}

	/**
	 * Verifica se todas as novas aulas a serem marcadas cabem no calendario desta instalacao
	 * @param instalacao - instalacao a verificar o calendario
	 * @param marcadas - aulas que se pretendem ativar nesta instalacao
	 * @return true se todas as aulas poderem ser ativadas nesta instalacao
	 */
	public boolean cabeNoCalendario(String instalacao,List<AulaAtiva> marcadas) {
		return this.instalacoes.get(instalacao).cabeNoCalendario(marcadas);
	}

	/**
	 * Ativa aulas no calendario de uma instalacao
	 * @param instalacao - instalacao onde se vao ativar as aulas
	 * @param marcadas - aulas que irao ser ativadas
	 */
	public void marcarAulas(String instalacao, List<AulaAtiva> marcadas) {
		Instalacao i = this.instalacoes.get(instalacao);
		i.marcarAulas(marcadas);
		this.instalacoes.replace(instalacao, i);
	}

	/**
	 * Visualiza a ocupacao da instalacao no dia indicado
	 * @param instalacao - instalacao a visualizar
	 * @param data - dia que se pretende ver a ocupacao
	 * @return lista com todas as sessoes que irao decorrer nesta instalacao
	 */
	public List<String> visualizarOcupacao(String instalacao, LocalDate data) {
		return this.instalacoes.get(instalacao).getSessoes(data);
	}

	/**
	 * Devolve a instalacao indicada
	 * @param instalacao - instalacao que se pretende obter
	 * @return o objeto da instalacao que se pretende devolver
	 */
	public Instalacao getInst(String instalacao) {
		return this.instalacoes.get(instalacao);
	}

}
