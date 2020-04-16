package business;

/**
 * Objeto que representa uma instalacao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Instalacao {

	private String nome;
	private int capacidade;
	private TipoInstalacao tipo;
	private List<AulaAtiva> aulasAtivas;

	/**
	 * Cria uma nova instalacao
	 * @param nome - nome da instalacao
	 * @param capacidade - capacidade desta instalacao
	 * @param tipo - tipo desta instalacao
	 */
	public Instalacao(String nome, int capacidade, TipoInstalacao tipo) {
		this.nome = nome;
		this.capacidade = capacidade;
		this.tipo = tipo;
		this.aulasAtivas = new ArrayList<>();
	}

	/**
	 * devolve o nome desta instalacao
	 * @return o nome desta instalacao
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Devolve a capacidade desta instalacao
	 * @return a capacidade desta instalacao
	 */
	public int getCapacidade() {
		return this.capacidade;
	}

	/**
	 * Devolve o tipo desta instalacao
	 * @return o tipo desta instalacao
	 */
	public TipoInstalacao getTipo() {
		return this.tipo;
	}

	/**
	 * Verifica se uma modalidade eh compativel com esta instalacao
	 * @param aulaModalidade - modalidade a verificar
	 * @return true se for compativel
	 */
	public boolean ehCompativel(Modalidade aulaModalidade) {
		return aulaModalidade.getInstalacaoCompativel().equals(this.tipo);
	}

	/**
	 * Verifica se todas estas aulas podem ser adicionadas ao calendario desta instalacao
	 * @param marcadas - aulas a verificar
	 * @return true se todas as aulas puderem ser marcadas no calendario desta instalacao
	 */
	public boolean cabeNoCalendario(List<AulaAtiva> marcadas) {
		if(this.aulasAtivas.isEmpty())
			return true;
		if(this.aulasAtivas.size() == 1) {
			return marcadas.stream()
					.allMatch(a -> this.aulasAtivas.get(0).possivelAntesDe(a) || this.aulasAtivas.get(0).possiveldDepoisDe(a));
		}else {
			return marcadas.stream()
					.allMatch(a -> a.cabeNoCalendario(this.aulasAtivas));
		}
	}

	/**
	 * Marca as aulas no calendario desta instalacao
	 * @param marcadas - aulas ativas a serem marcadas nesta instalacao
	 */
	public void marcarAulas(List<AulaAtiva> marcadas) {
		for(AulaAtiva am : marcadas)
			this.aulasAtivas.add(am);
		this.sortAulas();
	}

	/**
	 * Ordena as aulas de acordo com a data e hora
	 */
	private void sortAulas() {
		this.aulasAtivas= this.aulasAtivas.stream()
				.sorted((a1,a2)->a1.compareData(a2))
				.collect(Collectors.toList());
	}

	/**
	 * Devolve uma lista de sessoes nesta instalacao numa determinada data
	 * @param data - dia em que se pretende ver as sessoes na instalacao
	 * @return a lista de sessoes nesta instalacao
	 */
	public List<String> getSessoes(LocalDate data) {
		return this.aulasAtivas.stream()
				.filter(a -> a.ocorre(data))
				.sorted((a1,a2)->a1.compareData(a2))
				.map(AulaAtiva::infoSessao)
				.collect(Collectors.toList());
	}

}
