package business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import facade.exceptions.AppException;

/**
 * Catalogo que guarda as Instalacoes existentes no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoInstalacoes {

	private EntityManager em;

	/**
	 * Inicia o catalogo
	 */
	public CatalogoInstalacoes(EntityManager em) {
		this.em = em;
	}

	/**
	 * Devolve as instalacoes existentes
	 * @return lista das instalacoes
	 */
	public List<String> getInstalacoes() {
		try {
			TypedQuery<String> query = em.createNamedQuery(Instalacao.INSTALACAO_GET_NOMES , String.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>(); 
		}	
	}

	/**
	 * Devolve a instalacao indicada pelo nome
	 * @param instalacao - nome da instalacao que se pretende obter
	 * @return o objeto da instalacao que se pretende devolver
	 * @throws AppException 
	 */
	public Instalacao getInstalacaoByNome(String instalacao) throws AppException {
		try {
			TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.FIND_BY_NOME, Instalacao.class);
			query.setParameter(Instalacao.INSTALACAO_NOME, instalacao);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException ("A Instalacao nao existe", e);
		}
	}

	/**
	 * Devolve a instalacao indicada pelo id
	 * @param id - id da instalacao que se pretende obter
	 * @return o objeto da instalacao que se pretende devolver
	 * @throws AppException 
	 */
	public Instalacao getInstalacaoById(int id) throws AppException {
		try {
			TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.FIND_BY_ID, Instalacao.class);
			query.setParameter(Instalacao.INSTALACAO_ID, id);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException ("A Instalacao nao existe", e);
		}
	}
}
