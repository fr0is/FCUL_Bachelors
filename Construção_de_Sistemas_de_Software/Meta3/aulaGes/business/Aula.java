package business;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import facade.exceptions.AppException;

/**
 * Objeto que representa uma aula
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class Aula {

	private String nome;
	private LocalTime horaInicio; //formato tipo 12:00
	private int duracao;
	private int nrAlunos;
	private Instalacao instalacao;
	private Estado estado;
	private List<DiaSemana> diasSemana;
	private Modalidade modalidade;
	private List<AulaAtiva> aulasMarcadas;
	private List<Inscricao> regulares;
	private AulaAtivaFactory factory;

	/**
	 * Cria um novo objeto que representa uma aula
	 * @param modalidade - modalidade desta aula
	 * @param nomeAula - designacao desta aula
	 * @param dias - dias de semana em que esta aula ira decorrer
	 * @param horaInicio - hora de inicio desta aula
	 * @param duracao - duracao desta aula
	 * @throws AppException caso o formato da hora de inicio seja invalida
	 */
	public Aula(Modalidade modalidade, String nomeAula, List<String> dias, String horaInicio, int duracao) throws AppException {
		this.diasSemana = new ArrayList<>();
		this.aulasMarcadas = new ArrayList<>();
		this.regulares = new ArrayList<>();
		criarDias(dias);
		this.estado = Estado.INATIVA;
		this.nome = nomeAula;
		this.modalidade = modalidade;
		this.duracao = duracao;
		String[] hNm = horaInicio.split(":");
		this.horaInicio = LocalTime.of(Integer.parseInt(hNm[0]),Integer.parseInt(hNm[1]));
		this.factory = new AulaAtivaFactory(this);
	}

	/**
	 * Define a instalacao que esta aula ira decorrer
	 * @param instalacao - instalacao que esta aula ira decorrer
	 */
	public void setInstalacao(Instalacao instalacao) {
		this.instalacao=instalacao;
	}

	/**
	 * converte os valores da lista para os enumerados do dia de semana correspondentes
	 * @param dias - dias de semana em que esta aula ira decorrer
	 * @throws AppException caso um dia nao exista
	 */
	private void criarDias(List<String> dias) throws AppException {
		for(String dia: dias) {
			switch(dia.toLowerCase()) {
			case "segunda": 
				this.diasSemana.add(DiaSemana.SEGUNDA);
				break;
			case "terca": 
				this.diasSemana.add(DiaSemana.TERCA);
				break;
			case "quarta": 
				this.diasSemana.add(DiaSemana.QUARTA);
				break;
			case "quinta": 
				this.diasSemana.add(DiaSemana.QUINTA);
				break;
			case "sexta": 
				this.diasSemana.add(DiaSemana.SEXTA);
				break;
			case "sabado": 
				this.diasSemana.add(DiaSemana.SABADO);
				break;
			case "domingo": 
				this.diasSemana.add(DiaSemana.DOMINGO);
				break;
			default:
				throw new AppException("Dia nao existente");
			}
		}	
	}

	/**
	 * Devolve o nome desta aula
	 * @return o nome desta aula
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Devolve a hora de inicio desta da aula
	 * @return a hora de inicio desta da aula
	 */
	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	/**
	 * Devolve a hora de fim desta da aula
	 * @return a hora de fim desta da aula
	 */
	public LocalTime getHoraFim() {
		return horaInicio.plusMinutes(duracao);
	}

	/**
	 * devolve a duracao da aula
	 * @return duracao da aula
	 */
	public int getDuracao() {
		return duracao;
	}

	/**
	 * Devolve o numero de alunos desta aula
	 * @return o numero de alunos desta aula
	 */
	public int getNrAlunos() {
		return nrAlunos;
	}

	/**
	 * devolve o preco por hora desta aula
	 * @return o preco por hora desta aula
	 */
	public double getPrecoHora() {
		return this.modalidade.getPrecoPorHora();
	}

	/**
	 * devolve o estado da aula
	 * @return o estado da aula
	 */
	public Estado getEstado() {
		return this.estado;
	}

	/**
	 * devolve a modalidade a que esta aula esta associada
	 * @return a modalidade desta aula
	 */
	public Modalidade getModalidade() {
		return this.modalidade;
	}

	/**
	 * devolve o numero de sessoes por semana
	 * @return numero de sessoes por semana
	 */
	public int nrSessoesPorSemana() {
		return this.diasSemana.size();
	}

	/**
	 * Verifica se esta aula esta ativa num periodo de tempo
	 * @param data1 - data de inicio
	 * @param data2 - data de fim
	 * @return true se a aula se encontra ativa no periodo de tempo indicado
	 */
	public boolean estaAtiva(LocalDate data1,LocalDate data2) {
		if(this.estado == Estado.INATIVA) {
			return false;
		}
		if(this.aulasMarcadas.size() == 1) {
			if(this.aulasMarcadas.get(0).getData().isBefore(data1) || this.aulasMarcadas.get(0).getData().isAfter(data2))
				return false;
		}else {
			for(int i = 0; i < this.aulasMarcadas.size()-1; i++) {
				if(this.aulasMarcadas.get(i).getData().isBefore(data1) && this.aulasMarcadas.get(i+1).getData().isAfter(data2)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Verifica se a aula simplesmente esta ativa ou nao
	 * @return true se a aula estiver ativa
	 */
	public boolean estaAtiva() {
		return this.estado.equals(Estado.ATIVA);
	}

	/**
	 * Ativa as aulas geradas de um periodo de tempo e inscreve nelas os utentes regulares se houverem vagas
	 * @param aulas - aulas a serem ativadas
	 */
	public void ativarAula(List<AulaAtiva> aulas){
		this.estado = Estado.ATIVA;
		for(AulaAtiva a : aulas) {
			for(Inscricao r : this.regulares) {
				if(a.temVagas())
					a.inscreve(r);
			}			
			this.aulasMarcadas.add(a);
		}
		this.sortAulas();
	}

	/**
	 * Ordena as aulas ativadas pela data e hora
	 */
	private void sortAulas() {
		this.aulasMarcadas= this.aulasMarcadas.stream()
				.sorted((a1,a2)->a1.compareData(a2))
				.collect(Collectors.toList());
	}

	/**
	 * Devolve uma representacao textual desta aula de acordo com o tipo de inscricao que contem as informacoes desta aula
	 * @param tipoInscricao - tipo de inscricao
	 * @return a representacao textual desta aula
	 */
	public String infoAula(int tipoInscricao) {
		StringBuilder sb = new StringBuilder("Nome: " + this.nome + "\n Horario: " + this.horaInicio.toString() + "\n Dias de semana: ");
		for(DiaSemana d: this.diasSemana) {
			sb.append(d.toString() + ", ");
		}
		sb.replace(sb.length()-2, sb.length(), "\n");
		sb.append(" Instalacao: " + this.instalacao.getNome() + "\n");
		sb.append(" Vagas:\n");
		List<AulaAtiva> aulas;
		LocalDateTime next24hrs = LocalDateTime.now().plusHours(24);
		if(tipoInscricao == 1) {
			aulas = this.aulasMarcadas.stream()
					.filter(a -> a.getDateTimeInicio().isBefore(next24hrs) && a.temVagas())
					.collect(Collectors.toList());
		}else {
			aulas = this.aulasMarcadas.stream()
					.filter(AulaAtiva::temVagas)
					.collect(Collectors.toList());
		}
		if(aulas.isEmpty()) {
			sb.append("Nao existem vagas para esta aula \n");
		}else {
			for(AulaAtiva a: aulas)
				sb.append(a.verVagas());
		}
		return sb.toString();
	}

	/**
	 * Compara a hora de inicio desta aula com outra aula
	 * @param a - aula a comparar com esta
	 * @return o valor de comparacao, negativo se a hora desta aula for inferior, positivo se for superior
	 */
	public int comparaHora(Aula a) {
		return this.getHoraInicio().compareTo(a.getHoraInicio());
	}

	/**
	 * Inscreve um utente na proxima aula ou em todas as aulas futuras dependente do tipo de inscricao
	 * @param inscricao - Inscricao a que o utente esta associado
	 */
	public void inscreve(Inscricao inscricao) {
		if(inscricao.getDescricao().equals("Avulsa")) {
			int i = 0;
			for(AulaAtiva a: this.aulasMarcadas) {
				if(a.getDateTimeInicio().isBefore(LocalDateTime.now().plusHours(24)) && a.temVagas()) {
					this.aulasMarcadas.set(i, a.inscreve(inscricao));
					break;				
				}else {
					i++;
				}
			}
		}else {
			this.regulares.add(inscricao);
			this.aulasMarcadas.replaceAll(a -> a.inscreve(inscricao));
		}
	}

	/**
	 * Devolve os dias de semana em que esta aula decorre
	 * @return os dias de semana em que esta aula decorre
	 */
	public List<DiaSemana> getDiasSemana() {
		return this.diasSemana;
	}

	/**
	 * Gera aulas para um determinado periodo de tempo mas nao as ativa
	 * @param data1 - data de inicio
	 * @param data2 - data de fim
	 * @param nrMaxAlunos - numero maximo de alunos para estas aulas
	 * @return as aulas geradas
	 */
	public List<AulaAtiva> gerarAulasAtivas(LocalDate data1, LocalDate data2, int nrMaxAlunos) {
		return this.factory.gerarAulas(data1, data2, nrMaxAlunos);
	}
}
