package business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Objeto que representa uma aula ativa
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AulaAtiva {

	private String nome;
	private LocalDate data;
	private List<Inscricao> inscritos;
	private int maxAlunos;
	private int duracao;
	private LocalTime horaInicio; 

	/**
	 * Cria uma aula ativa de acordo com a informacao dada
	 * @param nome - nome desta aula
	 * @param data - dia em que ira decorrer
	 * @param maxAlunos - maximo de alunos para esta aula
	 * @param horaInicio - hora a que esta aula comeca
	 * @param duracao - duracao desta aula
	 */
	public AulaAtiva(String nome, LocalDate data, int maxAlunos, LocalTime horaInicio,int duracao){
		this.nome = nome;
		this.data = data;
		this.maxAlunos = maxAlunos;
		this.inscritos = new ArrayList<>();
		this.duracao = duracao;
		this.horaInicio = horaInicio;
	}

	/**
	 * Inscreve um utente nesta aula
	 * @param inscricao - inscricao a que o utente esta associado
	 * @return o objeto desta aula
	 */
	public AulaAtiva inscreve(Inscricao inscricao) {
		if(this.inscritos.size() < maxAlunos) {
			this.inscritos.add(inscricao);
			return this;
		}
		return null;
	}

	/**
	 * Devolve a data desta aula
	 * @return a data desta aula
	 */
	public LocalDate getData() {
		return this.data;
	}

	/**
	 * Devolve o numero de utentes inscritos nesta aula
	 * @return numero de utentes inscritos
	 */
	public int getNrInscritos() {
		return this.inscritos.size();
	}

	/**
	 * Devolve a hora de inicio desta aula
	 * @return a hora de inicio desta aula
	 */
	public LocalTime getHoraInicio() {
		return this.horaInicio;
	}

	/**
	 * Devolve a hora de fim desta aula
	 * @return a hora de fim desta aula
	 */
	public LocalTime getHoraFim() {
		return this.horaInicio.plusMinutes(duracao);
	}

	/**
	 * Verifica se esta aula pode decorrer antes de uma data e hora
	 * @param seguinte - data e hora
	 * @return true se esta aula pode decorrer antes desta hora
	 */
	public boolean possivelAntesDe(LocalDateTime seguinte) {
		LocalDateTime esta = LocalDateTime.of(this.getData(), this.getHoraFim());
		return esta.isBefore(seguinte);
	}

	/**
	 * Verifica se esta aula pode decorrer depois de uma data e hora
	 * @param seguinte - data e hora
	 * @return true se esta aula pode decorrer depois desta hora
	 */
	public boolean possiveldDepoisDe(LocalDateTime anterior) {
		LocalDateTime esta = LocalDateTime.of(this.getData(), this.getHoraInicio());
		return esta.isAfter(anterior);
	}

	/**
	 * Verifica se esta aula pode decorrer antes de outra aula
	 * @param aulaMarc - outra aula ativa
	 * @return true se esta aula pode decorrer antes de outra aula
	 */
	public boolean possivelAntesDe(AulaAtiva aulaMarc) {
		LocalDateTime esta = LocalDateTime.of(this.getData(), this.getHoraFim());
		LocalDateTime seguinte = LocalDateTime.of(aulaMarc.getData(), aulaMarc.getHoraInicio());
		return esta.isBefore(seguinte);
	}

	/**
	 * Verifica se esta aula pode decorrer depois de outra aula
	 * @param aulaMarc - outra aula ativa
	 * @return true se esta aula pode decorrer depois de outra aula
	 */
	public boolean possiveldDepoisDe(AulaAtiva aulaMarc) {
		LocalDateTime esta = LocalDateTime.of(this.getData(), this.getHoraInicio());
		LocalDateTime anterior = LocalDateTime.of(aulaMarc.getData(), aulaMarc.getHoraFim());
		return esta.isAfter(anterior);
	}

	/**
	 * Verifica se esta aula ocorrer entre outras aulas
	 * @param aulas - aulas a serem comparadas
	 * @return true se esta aula puder decorrer entre outras
	 */
	public boolean cabeNoCalendario(List<AulaAtiva> aulas) {
		List<AulaAtiva> ord = aulas.stream()
				.sorted((a1,a2) -> a1.compareData(a2))
				.collect(Collectors.toList());
		for(int i = -1; i < ord.size() ; i++ ) {
			if(i == -1) {
				if(ord.get(i+1).possiveldDepoisDe(this))
					return true;
			}else if(i+1 >= ord.size()) {
				if(ord.get(i).possivelAntesDe(this))
					return true;				
			}else {
				if(ord.get(i).possivelAntesDe(this) && ord.get(i+1).possiveldDepoisDe(this))
					return true;
			}
		}
		return false;
	}

	/**
	 * Devolve a data e hora de inicio
	 * @return a data e hora de inicio
	 */
	public LocalDateTime getDateTimeInicio() {
		return LocalDateTime.of(this.data, this.getHoraInicio());
	}

	/**
	 * Devolve a data e hora de fim
	 * @return a data e hora de fim
	 */
	public LocalDateTime getDateTimeFim() {
		return LocalDateTime.of(this.data, this.getHoraFim());
	}

	/**
	 * compara a data e hora desta aula com outra
	 * @param a - outra aula com que se vai comparar esta
	 * @return o valor do comparador
	 */
	public int compareData(AulaAtiva a) {
		return this.getDateTimeFim().compareTo(a.getDateTimeInicio());
	}

	/**
	 * Verifica se esta aula ocorre num certo dia
	 * @param data - dia 
	 * @return true se esta aula decorre no dia indicado
	 */
	public boolean ocorre(LocalDate data) {
		return this.data.equals(data);
	}

	/**
	 * compara a hora desta aula com outra
	 * @param a - outra aula com que se vai comparar esta
	 * @return o valor do comparador
	 */
	public int compareHora(AulaAtiva a) {
		return this.horaInicio.compareTo(a.getHoraInicio());
	}

	/**
	 * Devolve uma representacao textual desta aula ativa indicado o nome e quando comeca e acaba
	 * @return representacao textual desta aula ativa
	 */
	public String infoSessao() {
		return this.nome + " : " + this.getHoraInicio().toString() + " - " + this.getHoraFim().toString();
	}

	/**
	 * Verifica se esta aula tem vagas ou nao
	 * @return true se houverem vagas
	 */
	public boolean temVagas() {
		return this.inscritos.size() < maxAlunos;
	}

	/**
	 * Devolve uma representacao textual desta aula ativa indicado o dia em que decorre e as vagas que ha
	 * @return representacao textual desta aula ativa
	 */
	public String verVagas() {
		return "\t"+this.data.toString() + " - " + (this.maxAlunos-this.inscritos.size()) + " vagas\n";
	}

}
