package oochess.app.facade.exceptions;

public class NoTornaumentAssociatedException extends Exception{

	public NoTornaumentAssociatedException() {
		super("Este desafio não pertence a nenhum torneio.");
	}
	
}
