import java.io.Serializable;
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

public abstract class Message implements Serializable{
	
	private static final long serialVersionUID = -6618166029168876116L;
	
	private boolean isText;
	private List<String> users; //Utilizadores que ainda nao viram esta mensagem
	private String user;
	private String day;
	private String time;
	private int keyIndex;
	private byte[] content;

	public Message(String user, byte[] content, int index, boolean isText) {
		this.users = new ArrayList<>();
		this.user = user;
		this.isText = isText;
		this.content = content;
		this.keyIndex = index;
	}
	
	public void markDateNTime() {
		Date date = new Date();
		SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat t = new SimpleDateFormat("HH:mm");
		this.day = d.format(date);
		this.time = t.format(date);
	}

	public Message(String[] information, boolean isText) {
		this.isText = isText;
		this.user = information[0];
		this.day = information[1];
		this.time = information[2];
		this.keyIndex = Integer.parseInt(information[3]);

		this.users = new ArrayList<>();

		for(int i = 4; i < information.length; i++)
			this.users.add(information[i]);
	}
	
	public int getKeyIndex() {
		return this.keyIndex;
	}

	public void sendTo(String user) {
		if(!user.equals("ADMIN") && !this.users.contains(user))
			this.users.add(user);
	}

	public void read(String user) {
		if(!user.equals("ADMIN") && this.users.contains(user))
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
	
	public byte[] getContent() {
		return this.content;
	}

	public List<String> getUsersThatDidNotSee(){
		return new ArrayList<>(this.users);
	}

	public abstract String displayMsg(String msg);
}
