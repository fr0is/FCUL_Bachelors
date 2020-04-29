/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */
public class MequieException extends Exception {

	private static final long serialVersionUID = 1L;

	public MequieException(){
		super();
	}

	public MequieException(String message) {
		super(message); 
	}
	
	public MequieException(String message, Throwable cause) {
		super(message, cause); 
	}
	
	public MequieException(Throwable cause) {
		super(cause); 
	}
}
