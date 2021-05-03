package oochess.app.facade.exceptions;

public class NoEligiblePlayersException extends Exception{
	
	public NoEligiblePlayersException() {
		super("Nao existem Jogadores elegiveis!");
	}
}
