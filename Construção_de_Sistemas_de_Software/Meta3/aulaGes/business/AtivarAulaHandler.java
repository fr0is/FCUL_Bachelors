package business;

import java.time.LocalDate;
import java.util.List;

import facade.exceptions.AppException;

/**
 * Handler que permite ativar uma aula existente num periodo dado
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AtivarAulaHandler {

	private CatalogoAulas ca;
	private CatalogoInstalacoes ci;

	/**
	 * Constroi o handler que permite ativar aulas dando os catalogos necessarios
	 * @param ca - Catalogo de aulas
	 * @param ci - Catalogo de Instalacoes
	 */
	public AtivarAulaHandler(CatalogoAulas ca, CatalogoInstalacoes ci) {
		this.ca = ca;
		this.ci = ci;
	}

	/**
	 * Funcao que inicia a ativacao de uma aula e devolve as instalacoes existentes
	 * @return instalacoes existentes
	 */
	public List<String> ativarAula() {
		return this.ci.getInstalacoes();
	}

	/**
	 * Ativa a aula indicada num certo periodo na instalacao indicada e define o numero maximo de alunos para essas sessoes
	 * @param desAula - designacao da aula a ser ativada
	 * @param instalacao - instalacao a atribuir
	 * @param d1 - data de inicio
	 * @param d2 - data de fim
	 * @param nrMaxAlunos - numero maximo de alunos para estas aulas definidas
	 * @throws AppException caso a aula ja se encontra ativa, o par de datas nao define um periodo no futuro,
	 * a instalacao nao existe ou nao eh compativel com a modalidade, a instalacao ja se encontra ocupada nesse periodo
	 * ou a capacidade da instalacao nao eh suficiente
	 */
	public void ativAulaInfo(String desAula, String instalacao, String d1, String d2, int nrMaxAlunos) throws AppException {
		LocalDate data1 = this.convert(d1);
		LocalDate data2 = this.convert(d2);

		if(!(this.ca.existeAula(desAula)) || this.ca.estaAtiva(desAula,data1,data2)) {
			throw new AppException("Aula ativa neste período");
		}
		if(data1.isBefore(LocalDate.now())) {
			throw new AppException("O par de datas nao define um periodo no futuro");
		}
		if(!this.ci.existeInstalacao(instalacao)) {
			throw new AppException("Instalacao nao existente");
		}
		if(!this.ci.instalacaoCompativel(instalacao, this.ca.getAulaModalidade(desAula))) {
			throw new AppException("Instalacao nao compativel");
		}

		List<AulaAtiva> marcadas = this.ca.gerarAulasAtivas(desAula,data1,data2,nrMaxAlunos);
		if(!this.ci.cabeNoCalendario(instalacao,marcadas)){
			throw new AppException("A instalacao que indicou encontra-se ocupada neste periodo");
		}

		if(!this.ci.capacidadeMaxValida(instalacao,nrMaxAlunos)) {
			throw new AppException("Capacidade da instalacao nao eh suficiente");
		}

		this.ca.ativarAula(desAula,marcadas,this.ci.getInst(instalacao));
		this.ci.marcarAulas(instalacao,marcadas);
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
