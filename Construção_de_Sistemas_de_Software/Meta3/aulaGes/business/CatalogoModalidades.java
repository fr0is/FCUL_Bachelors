package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Catalogo que guarda as Modalidades existentes no sistema
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class CatalogoModalidades {

	private Map<String, Modalidade> modalidades;

	/**
	 * Inicia o catalogo
	 */
	public CatalogoModalidades() {
		this.modalidades = new HashMap<>();
		this.loadData();
	}

	/**
	 * Carrega os dados do catalogo
	 */
	private void loadData() {
		modalidades.put("Ciclismo", new Modalidade("Ciclismo",30, TipoInstalacao.SALABICICLETAS, 0.23));
		modalidades.put("Ginastica", new Modalidade("Ginastica",50, TipoInstalacao.ESTUDIO, 1.40));
		modalidades.put("Natacao", new Modalidade("Natacao",45,TipoInstalacao.PISCINA, 1.00));
		modalidades.put("Tenis", new Modalidade("Tenis",10,TipoInstalacao.CAMPODETENIS, 2.00));
		modalidades.put("Futsal", new Modalidade("Futsal", 20, TipoInstalacao.ESTUDIO, 0.60));
		modalidades.put("BasketBall", new Modalidade("BasketBall", 25,TipoInstalacao.ESTUDIO, 1.05));
	}

	/**
	 * Devolve as modalidades existentes
	 * @return lista de modalidades
	 */
	public List<String> getModalidades() {
		List<String> mods = new ArrayList<>();
		for(String i: this.modalidades.keySet())
			mods.add(i);
		return mods;
	}

	/**
	 * Valida se a duracao eh superior ao minimo definido pela modalidade
	 * @param modalidade - modalidade a verificar
	 * @param duracao - duracao que se pretende atribuir a uma aula
	 * @return true se a duracao for superior ou igual ao minimo definido pela modalidade
	 */
	public boolean duracaoValida(String modalidade, int duracao) {
		return this.modalidades.get(modalidade).duracaoValida(duracao);
	}

	/**
	 * Devolve a modalidade indicada
	 * @param modalidade - modalidade que se pretende obter
	 * @return Objeto da modalidade indicada
	 */
	public Modalidade getModalidade(String modalidade) {
		return this.modalidades.get(modalidade);
	}

	/**
	 * Verifica se esta modalidade existe no sistema
	 * @param modalidade - modalidade a verificar
	 * @return true se a modalidade existe no sistema
	 */
	public boolean contem(String modalidade) {
		return this.modalidades.containsKey(modalidade);
	}

}
