package business;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

	private EntityManagerFactory emf;

	/**
	 * Constroi o handler que permite ativar aulas dando os catalogos necessarios
	 * @param ca - Catalogo de aulas
	 * @param ci - Catalogo de Instalacoes
	 */
	public AtivarAulaHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Funcao que inicia a ativacao de uma aula e devolve as instalacoes existentes
	 * @return instalacoes existentes
	 * @throws AppException 
	 */
	public List<String> ativarAula() throws AppException {
		EntityManager em = emf.createEntityManager();
		CatalogoInstalacoes ci = new CatalogoInstalacoes(em);
		try {
			em.getTransaction().begin();
			List<String> instalacoes = ci.getInstalacoes();
			em.getTransaction().commit();
			return instalacoes;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao iniciar a ativacao de uma aula.", e);
		} finally {
			em.close();
		}
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
	public void ativAulaInfo(String desAula, String instalacao, LocalDate data1, LocalDate data2, int nrMaxAlunos) throws AppException {
		EntityManager em = emf.createEntityManager();
		CatalogoInstalacoes ci = new CatalogoInstalacoes(em);
		CatalogoAulas ca = new CatalogoAulas(em);
		try {
			em.getTransaction().begin();

			Aula aula = ca.getAulaByNome(desAula); //Isto diz tambem se a aula existe ou nao

			if(aula.estaAtiva(data1, data2)) {
				throw new AppException("Aula ativa neste período");
			}
			if(data1.isBefore(MockDateTime.currentDate())) {
				throw new AppException("O par de datas nao define um periodo no futuro");
			}

			Instalacao inst = ci.getInstalacaoByNome(instalacao); //Isto tambem diz se a Instalacao existe ou nao

			if(!inst.ehCompativel(aula.getModalidade())) {
				throw new AppException("Instalacao nao compativel");
			}

			List<AulaAtiva> marcadas = aula.gerarAulasAtivas(data1, data2, nrMaxAlunos);
			if(!inst.cabeNoCalendario(marcadas)){
				throw new AppException("A instalacao que indicou encontra-se ocupada neste periodo");
			}

			if(inst.getCapacidade() < nrMaxAlunos) {
				throw new AppException("Capacidade da instalacao nao eh suficiente");
			}

			for(AulaAtiva a : marcadas) {
				em.persist(a);
			}

			aula = em.merge(aula);
			inst = em.merge(inst);
			aula.ativarAula(marcadas);
			inst.marcarAulas(marcadas);
			aula.setInstalacao(inst);

			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao ativar a aula com a informacao indicada.", e);
		} finally {
			em.close();
		}
	}
}
