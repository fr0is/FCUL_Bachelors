import java.util.Arrays;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class TextMessage extends Message {

	private String msg;
	
	public TextMessage(String user, String msg) {
		super(user, true);
		this.msg = msg;
	}

	public TextMessage(String[] information) {
		super(Arrays.copyOfRange(information,1,information.length),true);
		this.msg = information[0];
	}

	@Override
	public String displayMsg() {
		String msg = String.format("<%s - %s: %s> %s", super.getDay(), super.getTime(), super.getUser(),this.msg);
		return msg;
	}
	
	@Override
	public String msgInfo() {
		StringBuilder info = new StringBuilder("true&"+this.msg+"&"+super.getUser()+"&"+super.getDay()+"&"+super.getTime());
		for(String u : super.getUsersThatDidNotSee())
			info.append("&"+u);
		return info.toString();
	}

}
