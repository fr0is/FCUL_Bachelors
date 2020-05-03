package business;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import facade.exceptions.AppException;

/**
 * Handler que permite criar uma aula
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CriarAulaHandler {

	private EntityManagerFactory emf;

	/**
	 * Cria o handler para criar aulas dando os catalogos necessarios
	 * @param cm - Catalogo de modalidades
	 * @param ca - Catalogo de Aulas
	 */
	public CriarAulaHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Inicia a criacao de uma aula e devolve as Modalidades existentes
	 * @return modalidades existentes
	 * @throws AppException 
	 */
	public List<String> criarAula() throws AppException {
		EntityManager em = emf.createEntityManager();
		CatalogoModalidades cm = new CatalogoModalidades(em);
		try {
			em.getTransaction().begin();
			List<String> modalidades = cm.getModalidades();
			em.getTransaction().commit();
			return modalidades;
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao iniciar a criacao de uma aula.", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Cria a aula com a informacao fornecida
	 * @param modalidade - modalidade a ser praticada na aula
	 * @param nomeAula - designacao da aula
	 * @param dias - dias da semana em que esta aula ira decorrer
	 * @param horaInicio - hora a que esta aula comeca
	 * @param duracao - duracao da aula
	 * @throws AppException caso a modalidade nao exista, o nome da aula ou a duracao nao seja valido
	 */
	public void criarAulaInfo(String modalidade, String nomeAula, List<String> dias, LocalTime horaInicio, int duracao) throws AppException {
		EntityManager em = emf.createEntityManager();
		CatalogoModalidades cm = new CatalogoModalidades(em);
		CatalogoAulas ca = new CatalogoAulas(em);
		try {
			em.getTransaction().begin();

			Modalidade mod = cm.getModalidadeByName(modalidade); //Isto vai verificar se a modalidade existe ou nao

			if(!((nomeAula.length() == 6) || !temAlfaNumericos(nomeAula) || nomeAula.contains(" ") || !ca.ehUnico(nomeAula))) {
				throw new AppException("Nome da Aula nao eh valido");
			}
			/*
			 * Sabemos que a duracao tem de ser positiva (maior que 0),
			 * e que tem de ser superior a duracao minima de cada modalidade.
			 * Se essa duracao ja eh positiva decidimos comparar a duracao dada para esta funcao
			 * apenas com a duracao minima da modalidade, nao sendo necessario ver se eh maior
			 * do que zero
			 */
			if(!mod.duracaoValida(duracao)) {
				throw new AppException("Duracao nao eh valida");
			}
			ca.criarAula(mod, nomeAula, dias, horaInicio, duracao);

			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new AppException("Erro ao criar a aula.", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Funcao auxiliar que verifica se o nome da aula eh valido
	 * @param nomeAula - nome a analisar
	 * @return true caso o nome seja valido
	 */
	private boolean temAlfaNumericos(String nomeAula) {
		int ocorrencias = 0;
		for(int i = 0; i < nomeAula.length() && ocorrencias < 3; i++) {
			char c = nomeAula.charAt(i);
			if(Character.isDigit(c) && Character.isLetter(c)) {
				ocorrencias++;
			}	
		}
		return (ocorrencias >= 3);
	}
}
