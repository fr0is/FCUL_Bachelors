package business;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import facade.exceptions.AppException;

/**
 * Catalogo que guarda o tipo de Inscricoes existentes no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoInscricoes {

	private EntityManager em;

	/**
	 * Inicia o Catalogo
	 */
	public CatalogoInscricoes(EntityManager em) {
		this.em = em;
	}

	/**
	 * Devolve a inscricao do tipo indicado
	 * @param tipo - tipo de inscricao (1 - avulsa, 2 - regular)
	 * @return um novo objeto de inscricao
	 * @throws AppException caso o tipo de inscricao nao exista
	 */
	public Inscricao getInscricao(int tipo) throws AppException {
		try {
			TypedQuery<Inscricao> query = em.createNamedQuery(Inscricao.FIND_BY_ID, Inscricao.class);
			query.setParameter(Inscricao.INSCRICAO_ID, tipo);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException("Tipo Inscricao nao valido");
		}
	}

	public Inscricao refresh(Inscricao inscricao) {
		return em.find(Inscricao.class, inscricao.getId());
	}
}
