package business;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import facade.exceptions.AppException;

/**
 * Handler que permite ver a ocupacao de uma instalacao num dado dia
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class VisualizarOcupacaoHandler {

	private EntityManagerFactory emf;

	/**
	 * Cria o handler que permite ver a ocupacao de uma instalacao dando o catalogo necessario
	 * @param ci - Catalogo de instalacoes
	 */
	public VisualizarOcupacaoHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Visualiza a ocupacao da instalacao no dia indicado
	 * @param instalacao - instalacao a visualizar
	 * @param data - data que se pretende ver a ocupacao
	 * @return lista com todas as sessoes que irao decorrer nesta instalacao
	 * @throws AppException caso a instalacao nao seja valida
	 */
	public List<String> visualizarOcupacao(String instalacao, LocalDate data) throws AppException{
		EntityManager em = emf.createEntityManager();
		CatalogoInstalacoes ci = new CatalogoInstalacoes(em);
		try {
			em.getTransaction().begin();
			
			Instalacao inst = ci.getInstalacaoByNome(instalacao); //Isto tambem verifica se existe ou nao

			List<String> ocupacao = inst.getSessoes(data);
			
			em.getTransaction().commit();
			return ocupacao;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao visualizar a ocupacao da instalacao.", e);
		} finally {
			em.close();
		}
	}
}
