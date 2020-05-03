package business;

import static javax.persistence.CascadeType.ALL;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Objeto que representa uma instalacao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */

@Entity
@NamedQuery(name=Instalacao.FIND_BY_ID, query="SELECT i FROM Instalacao i WHERE i.id = :" + Instalacao.INSTALACAO_ID)
@NamedQuery(name=Instalacao.FIND_BY_NOME, query="SELECT i FROM Instalacao i WHERE i.nome = :" + Instalacao.INSTALACAO_NOME)
@NamedQuery(name=Instalacao.INSTALACAO_GET_NOMES, query="SELECT i.nome FROM Instalacao i")
@Table(name = "INSTALACAO")
public class Instalacao{

	public static final String FIND_BY_ID = "Instalacao.findById";
	public static final String INSTALACAO_ID = "id";

	public static final String FIND_BY_NOME = "Instalacao.findByNome";
	public static final String INSTALACAO_NOME = "nome";

	public static final String INSTALACAO_GET_NOMES = "Instalacao.getAllNomes";

	@Id @Column(name = "INSTALACAO_ID")
	private int id;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Column(name = "CAPACIDADE", nullable = false)
	private int capacidade;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO", nullable = false)
	private TipoInstalacao tipo;

	@ManyToMany
	@JoinTable(name = "INSTALACAO_MODALIDADE", 
	joinColumns = @JoinColumn(name = "INSTALACAO_ID"), 
	inverseJoinColumns = @JoinColumn(name = "MODALIDADE_ID"))
	private List<Modalidade> modalidades;

	@OneToMany(cascade = ALL) 
	@JoinColumn(name = "INSTALACAO_ID", referencedColumnName = "INSTALACAO_ID")
	private List<AulaAtiva> aulasAtivas;

	Instalacao(){}
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
		this.modalidades = new ArrayList<>();
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

	public void tornarCompativel(Modalidade modalidade) {
		this.modalidades.add(modalidade);
	}

	/**
	 * Verifica se uma modalidade eh compativel com esta instalacao
	 * @param aulaModalidade - modalidade a verificar
	 * @return true se for compativel
	 */
	public boolean ehCompativel(Modalidade aulaModalidade) {
		return this.modalidades.contains(aulaModalidade);
	}

	/**
	 * Verifica se todas estas aulas podem ser adicionadas ao calendario desta instalacao
	 * @param marcadas - aulas a verificar
	 * @return true se todas as aulas puderem ser marcadas no calendario desta instalacao
	 */
	public boolean cabeNoCalendario(List<AulaAtiva> marcadas) {
		this.sortAulas();
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
