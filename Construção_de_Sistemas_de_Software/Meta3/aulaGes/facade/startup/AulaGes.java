package facade.startup;

import business.CatalogoAulas;
import business.CatalogoInscricoes;
import business.CatalogoInstalacoes;
import business.CatalogoModalidades;
import business.CatalogoUtentes;
import business.CriarAulaHandler;
import business.InscreverAulaHandler;
import business.AtivarAulaHandler;
import business.VisualizarOcupacaoHandler;

/**
 * A aplicacao principal
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AulaGes {

	private CatalogoAulas catalogoAulas;
	private CatalogoInscricoes catalogoInscricoes;
	private CatalogoInstalacoes catalogoInstalacoes;
	private CatalogoModalidades catalogoModalidades;
	private CatalogoUtentes catalogoUtentes;
	private CriarAulaHandler criarAulaHandler;
	private AtivarAulaHandler ativarAulaHandler;
	private VisualizarOcupacaoHandler visualizarOcupacaoHandler;

	/**
	 * Efetua o inicio da aplicacao
	 */
	public AulaGes() {
		this.catalogoAulas = new CatalogoAulas();
		this.catalogoInscricoes = new CatalogoInscricoes();
		this.catalogoInstalacoes = new CatalogoInstalacoes();
		this.catalogoModalidades = new CatalogoModalidades();
		this.catalogoUtentes = new CatalogoUtentes();
		this.criarAulaHandler = new CriarAulaHandler(this.catalogoModalidades,this.catalogoAulas);
		this.ativarAulaHandler = new AtivarAulaHandler(this.catalogoAulas,this.catalogoInstalacoes);
		this.visualizarOcupacaoHandler = new VisualizarOcupacaoHandler(this.catalogoInstalacoes);
	}

	/**
	 * @return CriarAulaHandler
	 */
	public CriarAulaHandler getCriarAulaHandler() {
		return this.criarAulaHandler;
	}

	/**
	 * @return AtivarAulaHandler
	 */
	public AtivarAulaHandler getAtivarAulaHandler() {
		return this.ativarAulaHandler;
	}

	/**
	 * @return InscreverAulaHandler
	 */
	public InscreverAulaHandler getInscreverAulaHandler() {
		return new InscreverAulaHandler(this.catalogoModalidades,this.catalogoInscricoes,this.catalogoAulas,this.catalogoUtentes);
	}

	/**
	 * @return VisualizarOcupacaoHandler
	 */
	public VisualizarOcupacaoHandler getVisualizarOcupacaoHandler() {
		return this.visualizarOcupacaoHandler;
	}

}
