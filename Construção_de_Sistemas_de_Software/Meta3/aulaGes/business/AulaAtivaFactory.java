package business;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Objeto que gera Aulas ativas
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AulaAtivaFactory {

	private Aula aula;

	/**
	 * Cria este objeto dando uma aula
	 * @param aula - aula associado a este objeto
	 */
	public AulaAtivaFactory(Aula aula) {
		this.aula = aula;
	}

	/**
	 * Gera uma lista de aulas num certo periodo e de acordo com os dias de semana em que a aula decorre mas nao as ativa
	 * @param data1 - data de inicio
	 * @param data2 - data de fim
	 * @param nrMaxAlunos - numero maximo de alunos para estas aulas
	 * @return lista de aulas geradas
	 */
	public List<AulaAtiva> gerarAulas(LocalDate data1, LocalDate data2, int nrMaxAlunos){
		List<AulaAtiva> marcadas = new ArrayList<>();
		List<LocalDate> dias = gerarDias(data1, data2);
		for(LocalDate d : dias) {
			if(verificarDiaDaSemana(d)) {
				AulaAtiva nova = new AulaAtiva(aula.getNome(),d,nrMaxAlunos,aula.getHoraInicio(),aula.getDuracao());
				marcadas.add(nova);
			}
		}
		return marcadas;
	}


	/**
	 * Gera uma lista de dias num certo periodo
	 * @param data1 - data de inicio
	 * @param data2 - data de fim
	 * @return lista de dias gerados
	 */
	private List<LocalDate> gerarDias(LocalDate data1, LocalDate data2) {
		int days = (int) data1.until(data2, ChronoUnit.DAYS);
		return Stream.iterate(data1, d -> d.plusDays(1))
				.limit(days)
				.collect(Collectors.toList());
	}

	/**
	 * Verifica se este dia eh um dos dias de semana em que a aula decorre
	 * @param d - dia a verificar
	 * @return true se o dia eh um dos dias de semana em que a aula decorre
	 */
	private boolean verificarDiaDaSemana(LocalDate d) {
		return this.aula.getDiasSemana().contains(traduz(d.getDayOfWeek()));
	}

	/**
	 * Traduz o enumerado do objeto DayOfWeek para o enumerado desta aplicacao
	 * @param dow - enumerado do DayOfWeek
	 * @return enumerado desta aplicacao resultante
	 */
	private DiaSemana traduz(DayOfWeek dow) {
		switch(dow) {
		case MONDAY:
			return DiaSemana.SEGUNDA;
		case TUESDAY:
			return DiaSemana.TERCA;
		case WEDNESDAY:
			return DiaSemana.QUARTA;
		case THURSDAY:
			return DiaSemana.QUINTA;
		case FRIDAY:
			return DiaSemana.SEXTA;
		case SATURDAY:
			return DiaSemana.SABADO;
		default:
			return DiaSemana.DOMINGO;
		}
	}

}
