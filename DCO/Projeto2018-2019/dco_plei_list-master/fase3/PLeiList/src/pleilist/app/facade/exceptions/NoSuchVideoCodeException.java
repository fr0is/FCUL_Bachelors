package pleilist.app.facade.exceptions;

public class NoSuchVideoCodeException extends Exception {
	
	public NoSuchVideoCodeException() {
		super("Nao existe nenhum video com esse codigo na sua biblioteca pessoal ou na biblioteca geral.");
	}

}
