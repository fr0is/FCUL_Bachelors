package business;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import facade.exceptions.AppException;

/**
 * Catalogo que guarda os utentes no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoUtentes {

	private static final String MENSAGEMDEERRO = "O Utente nao existe";
	
	private EntityManager em;

	/**
	 * Inicia o catalogo
	 */
	public CatalogoUtentes(EntityManager em) {
		this.em = em;
	}

	/**
	 * Devolve o utente correspondente ao nrUtente
	 * @param nrUtente - numero de inscricao do utente
	 * @return o utente ou null caso o utente indicado nao exista
	 * @throws AppException se o Utente nao existe
	 */
	public Utente getUtenteByNr(int nrUtente) throws AppException {
		try {
			TypedQuery<Utente> query = em.createNamedQuery(Utente.FIND_BY_NUMEROINSCRICAO, Utente.class);
			query.setParameter(Utente.UTENTE_INSCRICAO, nrUtente);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException(MENSAGEMDEERRO);
		}
	}


	/**
	 * Devolve o utente correspondente ao nif
	 * @param nif - nif do utente
	 * @return o utente ou null caso o utente indicado nao exista
	 * @throws AppException se o Utente nao existe
	 */
	public Utente getUtenteByNif(int nif) throws AppException {
		try {
			TypedQuery<Utente> query = em.createNamedQuery(Utente.FIND_BY_NIF, Utente.class);
			query.setParameter(Utente.UTENTE_NIF, nif);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException(MENSAGEMDEERRO);
		}
	}

	/**
	 * Devolve o utente correspondente ao nnome dado
	 * @param nome - nome do utente
	 * @return o utente ou null caso o utente indicado nao exista
	 * @throws AppException se o Utente nao existe
	 */
	public Utente getUtenteByNome(String nome) throws AppException {
		try {
			TypedQuery<Utente> query = em.createNamedQuery(Utente.FIND_BY_NOME, Utente.class);
			query.setParameter(Utente.UTENTE_NOME, nome);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException(MENSAGEMDEERRO);
		}
	}
}
