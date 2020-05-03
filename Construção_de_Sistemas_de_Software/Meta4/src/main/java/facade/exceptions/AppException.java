package facade.exceptions;

/**
 * A excecao da aplicacao
 * 
 * @author Grupo 30
 * @author Alexandre Monteiro - fc51023
 * @author Antonio Frois - fc51050
 * @author Filipe Pedroso - fc51958
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Atira uma nova excecao com mensagem message
	 * @param message - mensagem
	 */
	public AppException(String message) {
		super(message);
	}

	/**
	 * Atira uma excecao com outra excecao encapsulada
	 *
	 * @param message - mensagem
	 * @param e - excecao encapsulada
	 */
	public AppException(String message, Exception e) {
		super(message, e);
	}
}
