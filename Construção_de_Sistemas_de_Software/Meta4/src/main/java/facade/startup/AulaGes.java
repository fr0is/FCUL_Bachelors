package facade.startup;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import business.AtivarAulaHandler;
import business.CriarAulaHandler;
import business.InscreverAulaHandler;
import business.VisualizarOcupacaoHandler;
import facade.exceptions.AppException;
import facade.services.AtivarAulaService;
import facade.services.CriarAulaService;
import facade.services.InscreverAulaService;
import facade.services.VisualizarOcupacaoService;

/**
 * A aplicacao principal
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AulaGes {

	private CriarAulaService cas;
	private AtivarAulaService aas;
	private VisualizarOcupacaoService vos;
	private EntityManagerFactory emf;

	public void run() throws AppException {
		try {
			this.emf = Persistence.createEntityManagerFactory("domain-model-jpa");
			this.cas = new CriarAulaService(new CriarAulaHandler(emf));
			this.aas = new AtivarAulaService(new AtivarAulaHandler(emf));
			this.vos = new VisualizarOcupacaoService(new VisualizarOcupacaoHandler(emf));
		} catch (Exception e) {
			throw new AppException("Error connecting database", e);
		}
	}

	public void stopRun() {
		emf.close();
	}

	public CriarAulaService getCriarAulaService() {
		return this.cas;
	}

	public AtivarAulaService getAtivarAulaService() {
		return this.aas;
	}

	public InscreverAulaService getInscreverAulaService() {
		return new InscreverAulaService(new InscreverAulaHandler(emf));
	}

	public VisualizarOcupacaoService getVisualizarOcupacaoService() {
		return this.vos;
	}

}
