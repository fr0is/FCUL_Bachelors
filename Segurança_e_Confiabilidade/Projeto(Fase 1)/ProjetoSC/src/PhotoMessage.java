import java.util.Arrays;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class PhotoMessage extends Message {

	private String photo;

	public PhotoMessage(String user, String photo) {
		super(user, false);
		this.photo = photo;
	}

	public PhotoMessage(String[] information) {
		super(Arrays.copyOfRange(information,1,information.length),false);
		this.photo = information[0];
	}

	public String getPhoto() {
		return this.photo;
	}

	@Override
	public String displayMsg() {
		String msg = String.format("<%s - %s> %s enviou %s", super.getDay(), super.getTime(), super.getUser(),this.photo);
		return msg;
	}

	@Override
	public String msgInfo() {
		StringBuilder info = new StringBuilder("false&"+this.photo+"&"+super.getUser()+"&"+super.getDay()+"&"+super.getTime());
		for(String u : super.getUsersThatDidNotSee())
			info.append("&"+u);
		return info.toString();
	}

}
