package business;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import facade.exceptions.AppException;

/**
 * Catalogo que guarda as Aulas no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoAulas {

	private Map<String,Aula> aulas;

	/**
	 * Inicia o Catalogo
	 */
	public CatalogoAulas() {
		this.aulas = new HashMap<>();
	}

	/**
	 * Verifica se uma aula existe
	 * @param nomeA - designacao da aula
	 * @return true se a aula existe
	 */
	public boolean existeAula(String nomeA) {
		return this.aulas.containsKey(nomeA);
	}

	/**
	 * Cria uma nova aula com a informaca dada e guarda-a no sistema
	 * @param modalidade - modalidade atribuida a nova aula
	 * @param nomeAula - designacao da aula
	 * @param dias - dias de semana em que a aula ira decorrer
	 * @param horaInicio - hora a que a aula comeca
	 * @param duracao - duracao da aula
	 * @throws AppException caso o formato da hora de inicio seja invalida
	 */
	public void criarAula(Modalidade modalidade, String nomeAula, List<String> dias, String horaInicio, int duracao) throws AppException {
		Aula aula = new Aula(modalidade, nomeAula, dias, horaInicio, duracao);
		this.aulas.put(nomeAula, aula);
	}

	/**
	 * Verifica se uma aula se encontra ativa no periodo de tempo indicado
	 * @param desAula - designacao da aula
	 * @param data1 - data de inicio
	 * @param data2 - data de fim
	 * @return true se a aula nao se encontra ativa neste periodo de tempo
	 */
	public boolean estaAtiva(String desAula, LocalDate data1, LocalDate data2) {
		return this.aulas.get(desAula).estaAtiva(data1,data2);
	}

	/**
	 * Devolve a modalidade de uma aula
	 * @param desAula - designacao da aula
	 * @return O objeto da modalidade assossicado a aula desAula
	 */
	public Modalidade getAulaModalidade(String desAula) {
		return this.aulas.get(desAula).getModalidade();
	}

	/**
	 * Devolve a hora de inicio de uma aula
	 * @param desAula - designacao da aula
	 * @return a hora de inicio da aula desAula
	 */
	public LocalTime getHoraInicio(String desAula) {
		return this.aulas.get(desAula).getHoraInicio();
	}

	/**
	 * Devolve a hora de fim de uma aula
	 * @param desAula - designacao da aula
	 * @return a hora de fim da aula desAula
	 */
	public LocalTime getHoraFim(String desAula) {
		return this.aulas.get(desAula).getHoraFim();
	}

	/**
	 * Ativa as aulas indicadas e atribui uma instalacao
	 * @param desAula - designacao da aula
	 * @param aulas - lista de aulas que vao ser ativadas
	 * @param instalacao - instalacao que se vai atribuir a aula
	 */
	public void ativarAula(String desAula, List<AulaAtiva> aulas, Instalacao instalacao){
		Aula a = this.aulas.get(desAula);
		a.setInstalacao(instalacao);
		a.ativarAula(aulas);
		this.aulas.replace(desAula, a);
	}

	/**
	 * Devolve as aulas ativas de uma modalidade
	 * @param modalidade - modalidade das aulas
	 * @return lista das aulas ativas associadas a uma modalidade
	 */
	public List<Aula> getAulasAtivas(String modalidade){
		List<Aula> aulasss = new ArrayList<>();
		for(Aula a : this.aulas.values()) {
			if(a.estaAtiva() && a.getModalidade().getNome().equals(modalidade))
				aulasss.add(a);
		}			
		return aulasss;
	}

	/**
	 * Inscreve um utente na aula indicada
	 * @param desAula - designacao da aula
	 * @param inscricao - inscricao a que o utente esta associado
	 * @return aula em que o utente se inscreveu
	 */
	public Aula inscreve(String desAula, Inscricao inscricao){
		Aula a = this.aulas.get(desAula);
		a.inscreve(inscricao);
		this.aulas.replace(desAula, a);
		return a;
	}

	/**
	 * Gera sessoes para uma aula num periodo de tempo mas nao as ativas ainda
	 * @param desAula - designacao da aula
	 * @param data1 - data de inicio
	 * @param data2 - date de fim
	 * @param nrMaxAlunos - numero maximo de alunos para as aulas
	 * @return lista das aulas geradas
	 */
	public List<AulaAtiva> gerarAulasAtivas(String desAula, LocalDate data1, LocalDate data2, int nrMaxAlunos) {
		return this.aulas.get(desAula).gerarAulasAtivas(data1,data2,nrMaxAlunos);
	}

}
