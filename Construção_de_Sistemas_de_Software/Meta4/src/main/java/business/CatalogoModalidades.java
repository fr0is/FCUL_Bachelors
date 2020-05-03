package business;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import facade.exceptions.AppException;

/**
 * Catalogo que guarda as Modalidades existentes no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoModalidades {

	private EntityManager em;

	/**
	 * Inicia o catalogo
	 */
	public CatalogoModalidades(EntityManager em) {
		this.em = em;
	}

	/**
	 * Devolve as modalidades existentes
	 * @return lista de modalidades
	 */
	public List<String> getModalidades() {
		try {
			TypedQuery<String> query = em.createNamedQuery(Modalidade.MODALIDADE_GET_NOMES, String.class);
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<>(); 
		}	
	}

	/**
	 * Devolve a modalidade indicada pelo id
	 * @param id - id da modalidade que se pretende obter
	 * @return Objeto da modalidade indicada
	 * @throws AppException se a modalidade nao existe
	 */
	public Modalidade getModalidadeById(int id) throws AppException {
		try {
			TypedQuery<Modalidade> query = em.createNamedQuery(Modalidade.FIND_BY_ID, Modalidade.class);
			query.setParameter(Modalidade.MODALIDADE_ID, id);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException ("A Modalidade nao existe", e);
		}
	}
	
	/**
	 * Devolve a modalidade indicada pelo nome
	 * @param modalidade - nome da modalidade que se pretende obter
	 * @return Objeto da modalidade indicada
	 * @throws AppException se a modalidade nao existe
	 */
	public Modalidade getModalidadeByName(String modalidade) throws AppException {
		try {
			TypedQuery<Modalidade> query = em.createNamedQuery(Modalidade.FIND_BY_NOME, Modalidade.class);
			query.setParameter(Modalidade.MODALIDADE_NOME, modalidade);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException ("A Modalidade nao existe", e);
		}
	}
}
