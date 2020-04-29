import java.io.Serializable;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class TextMessage extends Message implements Serializable{
	
	private static final long serialVersionUID = -8479269755886034594L;
	
	public TextMessage(String user, byte[] content, int index) {
		super(user, content, index, true);
	}

	@Override
	public String displayMsg(String msg) {
		return String.format("<%s - %s: %s> %s", super.getDay(), super.getTime(), super.getUser(),msg);
	}
}
