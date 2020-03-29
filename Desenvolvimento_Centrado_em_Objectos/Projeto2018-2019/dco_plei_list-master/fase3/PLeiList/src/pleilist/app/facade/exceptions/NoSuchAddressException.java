package pleilist.app.facade.exceptions;

public class NoSuchAddressException extends Exception {
	
	public NoSuchAddressException() {
		super("Esse endereco nao existe.");
	}
	
}