package business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import facade.exceptions.AppException;

/**
 * Servico que permite inscrever utentes em aulas de acordo com o tipo de inscricao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class InscreverAulaHandler {

	private CatalogoModalidades cm;
	private CatalogoAulas ca;
	private CatalogoInscricoes ci;
	private CatalogoUtentes cu;
	private List<String> modalidades;
	private Inscricao inscricao;
	private List<Aula> aulasAtivas;

	/**
	 * Cria o handler que permite inscrever utentes
	 * @param cm - Catalogo de Modalidades
	 * @param ci - Catalogo de Inscricoes
	 * @param ca - Catalogo de Aulas
	 * @param cu - Catalogo de Utentes
	 */
	public InscreverAulaHandler(CatalogoModalidades cm, CatalogoInscricoes ci, CatalogoAulas ca, CatalogoUtentes cu) {
		this.cm = cm;
		this.ci = ci;
		this.ca = ca;
		this.cu = cu;
	}

	/**
	 * Inicia a inscricao numa aula e devolve as modalidades que existem
	 * @return Modalidades existentes
	 */
	public List<String> inscreverAula() {
		this.modalidades = this.cm.getModalidades();
		return this.modalidades;
	}

	/**
	 * Devolve todas as aulas ativas em que o utente se pode inscrever de acordo com a modalidade escolhida e o tipo de inscricao
	 * @param modalidade - modalidade escolhida
	 * @param tipoInscricao - tipo de inscricao a fazer
	 * @return aulas ativas em que o utente se pode inscrever
	 * @throws AppException caso a modalidade e o tipo de inscricao nao forem validos
	 */
	public List<String> infoInscreverAula(String modalidade, int tipoInscricao) throws AppException {
		if(!this.modalidades.contains(modalidade)) {
			throw new AppException("Modalidade nao existente");
		}
		this.inscricao =  this.ci.getInscricao(tipoInscricao);

		this.aulasAtivas = this.ca.getAulasAtivas(modalidade);
		
		if(this.aulasAtivas.isEmpty())
			return new ArrayList<>();
		
		return this.aulasAtivas.stream()
				.sorted((a1,a2) -> a1.comparaHora(a2) )
				.map(a -> a.infoAula(tipoInscricao))
				.collect(Collectors.toList());
	}

	/**
	 * Efetua a inscricao concreta do utente na aula definida por este e devolve o custo
	 * @param desAula - aula em que o utente se vai inscrever
	 * @param nrUtente - o numero de inscricao do utente
	 * @return custo da inscricao de acordo com o tipo que foi definido
	 * @throws AppException caso o tipo de inscricao nao tenha sido indicado, a aula nao seja valida ou utente nao exista
	 */
	public double escolheAula(String desAula, int nrUtente) throws AppException {
		if(this.inscricao == null)
			throw new AppException("Nao foi indicado o tipo de inscricao");

		if(!this.ehValido(desAula))
			throw new AppException("A aula indicada nao eh valida");

		Utente u = this.cu.getUtente(nrUtente);
		if(u == null)
			throw new AppException("O utente nao existe");
		this.inscricao.setUtente(u);

		Aula a = this.ca.inscreve(desAula,this.inscricao);
		return this.inscricao.calcularCusto(a);
	}

	/**
	 * Verifica que a aula eh valida
	 * @param desAula - aula a verificar
	 * @return true caso a aula seja valida
	 */
	private boolean ehValido(String desAula) {
		for(Aula a:this.aulasAtivas) {
			if(a.getNome().equals(desAula))
				return true;
		}
		return false;
	}

}
