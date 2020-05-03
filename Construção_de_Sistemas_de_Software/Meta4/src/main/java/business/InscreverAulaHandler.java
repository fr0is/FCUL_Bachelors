package business;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

	private List<String> modalidades;
	private Inscricao inscricao;
	private List<Aula> aulasAtivas;
	private EntityManagerFactory emf;

	/**
	 * Cria o handler que permite inscrever utentes
	 * @param cm - Catalogo de Modalidades
	 * @param ci - Catalogo de Inscricoes
	 * @param ca - Catalogo de Aulas
	 * @param cu - Catalogo de Utentes
	 */
	public InscreverAulaHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Inicia a inscricao numa aula e devolve as modalidades que existem
	 * @return Modalidades existentes
	 * @throws AppException 
	 */
	public List<String> inscreverAula() throws AppException {
		EntityManager em = emf.createEntityManager();
		CatalogoModalidades cm = new CatalogoModalidades(em);
		try {
			em.getTransaction().begin();
			this.modalidades = cm.getModalidades();
			em.getTransaction().commit();
			return this.modalidades;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao iniciar a inscricao numa aula.", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Devolve todas as aulas ativas em que o utente se pode inscrever de acordo com a modalidade escolhida e o tipo de inscricao
	 * @param modalidade - modalidade escolhida
	 * @param tipoInscricao - tipo de inscricao a fazer
	 * @return aulas ativas em que o utente se pode inscrever
	 * @throws AppException caso a modalidade e o tipo de inscricao nao forem validos
	 */
	public List<String> infoInscreverAula(String modalidade, int tipoInscricao) throws AppException {
		EntityManager em = emf.createEntityManager();
		CatalogoAulas ca = new CatalogoAulas(em);
		CatalogoInscricoes ci = new CatalogoInscricoes(em);
		try {
			em.getTransaction().begin();
			if(!this.modalidades.contains(modalidade)) {
				throw new AppException("Modalidade nao existente");
			}
			this.inscricao =  ci.getInscricao(tipoInscricao);

			this.aulasAtivas = ca.getAulasAtivas(modalidade);

			List<String> aulasAtivasInfo = this.aulasAtivas.stream()
					.sorted((a1,a2) -> a1.comparaHora(a2) )
					.map(a -> a.infoAula(tipoInscricao))
					.collect(Collectors.toList());

			em.getTransaction().commit();
			return aulasAtivasInfo;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao devolver todas as aulas ativas disponiveis.", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Efetua a inscricao concreta do utente na aula definida por este e devolve o custo
	 * @param desAula - aula em que o utente se vai inscrever
	 * @param nrUtente - o numero de inscricao do utente
	 * @return custo da inscricao de acordo com o tipo que foi definido
	 * @throws AppException caso o tipo de inscricao nao tenha sido indicado, a aula nao seja valida ou utente nao exista
	 */
	public double escolheAula(String desAula, int nrUtente) throws AppException {
		EntityManager em = emf.createEntityManager();
		CatalogoAulas ca = new CatalogoAulas(em);
		CatalogoUtentes cu = new CatalogoUtentes(em);
		CatalogoInscricoes ci = new CatalogoInscricoes(em);
		try {
			em.getTransaction().begin();
			if(this.inscricao == null)
				throw new AppException("Nao foi indicado o tipo de inscricao");

			Utente u = cu.getUtenteByNr(nrUtente); //Tambem verifica se o utente existe ou nao
			this.inscricao = ci.refresh(this.inscricao).novaInscricao(u);
			em.persist(this.inscricao);	
			
			this.aulasAtivas = this.aulasAtivas.stream()
					.map(a -> em.find(Aula.class,a.getId()))
					.collect(Collectors.toList());

			if(!this.ehValido(desAula))
				throw new AppException("A aula indicada nao eh valida");
			
			Aula a = ca.getAulaByNome(desAula);
			em.merge(a);
			a.inscreve(this.inscricao);
			double custo = this.inscricao.calcularCusto(a);

			em.getTransaction().commit();
			return round(custo, 2);
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao finalizar a inscricao numa aula.", e);
		} finally {
			em.close();
		}
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
	
	/**
	 * metodo que arredonda um numero com um certo numero de casa decimais
	 * @param value - numero a arredondar
	 * @param places - casas decimais
	 * @return numero arredondado
	 */
	private double round(double value, int places) {
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}

}
