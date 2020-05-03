package business;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import facade.exceptions.AppException;

/**
 * Catalogo que guarda as Aulas no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoAulas {

	private EntityManager em;

	/**
	 * Inicia o Catalogo
	 */
	public CatalogoAulas(EntityManager em) {
		this.em = em;
	}

	/**
	 * Cria uma nova aula com a informaca dada e guarda-a no sistema
	 * @param modalidade - modalidade atribuida a nova aula
	 * @param nomeAula - designacao da aula
	 * @param dias - dias de semana em que a aula ira decorrer
	 * @param horaInicio - hora a que a aula comeca
	 * @param duracao - duracao da aula
	 * @throws AppException caso o formato da hora de inicio seja invalida
	 */
	public void criarAula(Modalidade modalidade, String nomeAula, List<String> dias, LocalTime horaInicio, int duracao) throws AppException {
		Aula aula = new Aula(modalidade, nomeAula, dias, horaInicio, duracao);
		em.persist(aula);
	}
	
	/**
	 * Devolve a Aula indicada pelo id
	 * @param id - id da Aula
	 * @return Objeto Aula
	 * @throws AppException se a Aula nao existe
	 */
	public Aula getAulaById(int id) throws AppException {
		try {
			TypedQuery<Aula> query = em.createNamedQuery(Aula.FIND_BY_ID, Aula.class);
			query.setParameter(Aula.AULA_ID, id);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException ("A Aula nao existe", e);
		}
	}
	
	/**
	 * Devolve a Aula indicada pelo nome
	 * @param nome - nome da Aula
	 * @return Objeto Aula
	 * @throws AppException se a Aula nao existe
	 */
	public Aula getAulaByNome(String nome) throws AppException {
		try {
			TypedQuery<Aula> query = em.createNamedQuery(Aula.FIND_BY_NOME, Aula.class);
			query.setParameter(Aula.AULA_NOME, nome);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new AppException ("A Aula nao existe", e);
		}
	}

	/**
	 * Devolve as aulas ativas de uma modalidade
	 * @param modalidade - modalidade das aulas
	 * @return lista das aulas ativas associadas a uma modalidade
	 * @throws AppException 
	 */
	public List<Aula> getAulasAtivas(String modalidade) throws AppException{
		try {
			TypedQuery<Aula> query = em.createNamedQuery(Aula.FIND_ALL, Aula.class);			
			List<Aula> aulasss = new ArrayList<>();
			for(Aula a : query.getResultList()) {
				if(a.estaAtiva() && a.getModalidade().getNome().equals(modalidade))
					aulasss.add(a);
			}			
			if(aulasss.isEmpty())
				throw new AppException("Nao existem aulas ativas para " + modalidade);
			return aulasss;
		} catch (Exception e) {
			throw new AppException("Nao existem aulas ativas para " + modalidade); 
		}
	}

	/**
	 * Verifica se o nome e unico
	 * @param nomeAula - nome a verificar
	 * @return true se o nome for unico
	 */
	public boolean ehUnico(String nomeAula) {
		try {
			TypedQuery<Aula> query = em.createNamedQuery(Aula.FIND_BY_NOME, Aula.class);
			query.setParameter(Aula.AULA_NOME, nomeAula);
			query.getSingleResult();
			return false;
		} catch (Exception e) {
			return true;
		}
	}
}
