package oochess.app.facade.exceptions;

public class UserAlreadyExistsException extends Exception{

	public UserAlreadyExistsException() {
		super("Nome de utilizador já registado. Insira outro");
	}
	
}
