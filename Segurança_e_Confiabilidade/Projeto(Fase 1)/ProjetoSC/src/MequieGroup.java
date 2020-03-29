import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class MequieGroup {

	private static final String GROUPPATH = "serverData/groupCatalogData/";

	private String id;
	private String owner;
	private List<String> users;
	private Queue<Message> msgs;
	private File inbox;
	private File history;
	private String photoPath;

	public MequieGroup(String id, String owner) {
		this.id = id;
		this.owner = owner;
		this.users = new ArrayList<>();
		this.msgs = new LinkedList<>();
		this.users.add(owner);

		this.inbox = new File(GROUPPATH + "/" + this.id + "/inbox");
		if(!this.inbox.exists()) {
			this.inbox.getParentFile().mkdirs();
			try {
				this.inbox.createNewFile();
			} catch (IOException e) {
				System.out.println(id + ": Erro ao gerar ficheiro da caixa de entrada");
			}
		}

		this.history  = new File(GROUPPATH + "/" + this.id + "/history");
		if(!this.history.exists()) {
			this.history.getParentFile().mkdirs();
			try {
				this.history.createNewFile();
			} catch (IOException e) {
				System.out.println(id + ": Erro ao gerar ficheiro do historico");
			}
		}

		this.photoPath = GROUPPATH + "/" + this.id + "/photoBox/";
	}

	public MequieGroup(String[] information) {
		this.id = information[0];
		this.owner = information[1];
		this.users = new ArrayList<>();
		this.msgs = new LinkedList<>();
		for(int i = 2; i < information.length; i++) {
			this.users.add(information[i]);
		}
		this.inbox = new File(GROUPPATH + "/" + this.id + "/inbox");
		this.history  = new File(GROUPPATH + "/" + this.id + "/history");
		this.photoPath = GROUPPATH + "/" + this.id + "/photoBox/";

		loadInbox();
	}

	public String getId() {
		return this.id;
	}

	public boolean isOwner(String user) {
		return user.equals(this.owner);
	}

	public boolean addUser(String targetUser) {
		Queue<Message> temp = new LinkedList<>();
		for(Message m : this.msgs) {
			m.sendTo(targetUser);
			temp.add(m);
		}
		this.msgs = temp;
		this.checkForReadedMsgs();
		this.updateInbox();
		return this.users.add(targetUser);
	}

	public boolean isUserIn(String targetUser) {
		return this.users.contains(targetUser);
	}

	public boolean removeUser(String targetUser) {
		Queue<Message> temp = new LinkedList<>();
		for(Message m : this.msgs) {
			m.read(targetUser);
			temp.add(m);
		}
		this.msgs = temp;
		this.checkForReadedMsgs();
		this.updateInbox();
		return this.users.remove(targetUser);
	}

	public String dataInfo() {
		StringBuilder info = new StringBuilder(this.id + "/" + this.owner);
		for(String u : this.users)
			info.append("/" + u);
		return info.toString();
	}

	public String info(String user) {
		StringBuilder info = new StringBuilder(this.owner + " " + this.users.size());
		if(this.isOwner(user)) 
			for(String u : this.users)
				info.append(" " + u);
		return info.toString();
	}

	public void sendToInbox(Message message) {
		for(String u : this.users)
			message.sendTo(u);
		this.msgs.add(message);
		this.checkForReadedMsgs();
		this.updateInbox();
	}

	public void checkForReadedMsgs() {
		try {
			Writer w = new BufferedWriter(new FileWriter(this.history, true));
			List<String> pics = new ArrayList<>();
			while(this.msgs.peek() != null && this.msgs.peek().everyoneSaw()) {
				Message m = this.msgs.remove();
				if(m.isText()) {
					w.append(m.displayMsg() + "\n");
				}else {
					pics.add(((PhotoMessage) m).getPhoto());
				}
			}
			w.close();
			checkPictures(pics);
		} catch (IOException e) {
			System.out.println(this.id + ": Erro ao escrever no historico do grupo");
		}
	}

	private void checkPictures(List<String> pics) {
		for(String p : pics) {
			boolean Found = false;
			for(Message m : this.msgs) {
				if(!m.isText() && p.equals(((PhotoMessage) m).getPhoto()))
					Found = true;
			}
			if(!Found) {
				File pic = new File(this.photoPath+p);
				if(pic.exists())
					pic.delete();
			}
		}
	}

	public File history() {
		return this.history;
	}

	public void updateInbox() {
		try {
			Writer w = new FileWriter(this.inbox);
			if(!this.msgs.isEmpty()) {
				for(Message m : this.msgs)
					w.write(m.msgInfo() + "&\n");
			}
			w.close();
		} catch (IOException e) {
			System.out.println(this.id + ": Erro ao atualizar a caixa de entrada deste grupo");
		}
	}

	private void loadInbox() {
		try {
			Scanner sc = new Scanner(this.inbox);
			String msg;
			String[] information;
			while(sc.hasNextLine()) {
				msg = sc.nextLine();
				information = msg.split("&");
				Message m;
				if(information[0].equals("true")) {
					m = new TextMessage(Arrays.copyOfRange(information,1,information.length));
				}else {
					m = new PhotoMessage(Arrays.copyOfRange(information,1,information.length));
				}
				this.msgs.add(m);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println(this.id + ": Erro ao carregar a caixa de entrada deste grupo");
		}
	}

	public String[] collect(String user){
		Queue<Message> temp = new LinkedList<>();
		List<String> messages = new ArrayList<>();
		List<String> photos = new ArrayList<>();
		int msg = 0; //numero de mensagens recolhidas
		int pho = 0; //numero de fotos a enviar

		for(Message m : this.msgs) {
			if(m.wasNotSeenBy(user)) {
				m.read(user);
				messages.add(m.displayMsg());
				msg++;
				if(!m.isText()) {
					String photo =  ((PhotoMessage) m).getPhoto();
					photos.add(this.photoPath + photo);
					pho++;
				}
			}
			temp.add(m);
		}
		this.msgs = temp;

		String[] collected = new String[2+msg+pho];
		collected[0] = String.valueOf(msg);
		collected[1] = String.valueOf(pho);
		if(msg + pho != 0)
			collected = gatherIntoArray(collected,messages,photos);
		return collected;
	}

	private String[] gatherIntoArray(String[] collected, List<String> messages, List<String> photos) {
		String[] neew = new String[collected.length];
		neew[0] = collected[0];
		neew[1] = collected[1];

		int pivot = 2;
		for(String m : messages) {
			neew[pivot] = m;
			pivot++;
		}
		for(String p : photos) {
			neew[pivot] = p;
			pivot++;
		}

		return neew;
	}

}
