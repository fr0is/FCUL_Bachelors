import java.io.Serializable;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class PhotoMessage extends Message implements Serializable{
	
	private static final long serialVersionUID = 4630020427288872155L;
	
	private String photo;

	public PhotoMessage(String user, byte[] content, String photo, int index) {
		super(user, content, index, false);
		this.photo = photo;
	}

	public String getPhotoName() {
		return this.photo;
	}

	@Override
	public String displayMsg(String photo) {
		return String.format("<%s - %s> %s enviou %s", super.getDay(), super.getTime(), super.getUser(),this.photo);
	}
}
