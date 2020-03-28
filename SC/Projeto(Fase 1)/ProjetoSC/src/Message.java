import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public abstract class Message {

	private boolean isText;
	private List<String> users; //Utilizadores que ainda nao viram esta mensagem
	private String user;
	private String day;
	private String time;

	public Message(String user, boolean isText) {
		Date date = new Date();
		SimpleDateFormat day = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat time = new SimpleDateFormat("HH:mm");

		this.users = new ArrayList<>();

		this.user = user;
		this.day = day.format(date);
		this.time = time.format(date);
		this.isText = isText;
	}

	public Message(String[] information, boolean isText) {
		this.isText = isText;
		this.user = information[0];
		this.day = information[1];
		this.time = information[2];

		this.users = new ArrayList<>();

		for(int i = 3; i < information.length; i++)
			this.users.add(information[i]);
	}

	public void sendTo(String user) {
		if(!user.equals("ADMIN"))
			this.users.add(user);
	}

	public void read(String user) {
		if(!user.equals("ADMIN"))
			this.users.remove(user);
	}

	public boolean wasNotSeenBy(String user) {
		return this.users.contains(user);
	}

	public boolean everyoneSaw() {
		return this.users.isEmpty();
	}

	public String getUser() {
		return this.user;
	}

	public String getDay() {
		return this.day;
	}

	public String getTime() {
		return this.time;
	}

	public boolean isText() {
		return this.isText;
	}

	public List<String> getUsersThatDidNotSee(){
		return new ArrayList<>(this.users);
	}

	public abstract String displayMsg();

	public abstract String msgInfo();
}
