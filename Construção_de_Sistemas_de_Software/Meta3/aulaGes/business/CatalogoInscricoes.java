package business;

import java.util.HashMap;
import java.util.Map;

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

	private Map<Integer,Inscricao> inscricoes;

	/**
	 * Inicia o Catalogo
	 */
	public CatalogoInscricoes() {
		this.inscricoes = new HashMap<>();
		loadData();
	}

	/**
	 * Devolve a inscricao do tipo indicado
	 * @param tipo - tipo de inscricao (1 - avulsa, 2 - regular)
	 * @return um novo objeto de inscricao
	 * @throws AppException caso o tipo de inscricao nao exista
	 */
	public Inscricao getInscricao(int tipo) throws AppException {
		Inscricao i = inscricoes.get(tipo);
		if(i == null) {
			throw new AppException("Tipo Inscricao nao valido");		
		}
		return i;
	}

	/**
	 * Carrega os dados do catalogo
	 */
	private void loadData() {
		inscricoes.put(1, new InscricaoAvulsa("Avulsa"));
		inscricoes.put(2, new InscricaoRegular("Regular"));
	}

}
