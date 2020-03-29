package pleilist.app.facade.exceptions;

public class NoSuchVideoWithHashtagException extends Exception{

	public NoSuchVideoWithHashtagException() {
		super("Nao existe nenhum video com esse hashtag.");
	}
	
}