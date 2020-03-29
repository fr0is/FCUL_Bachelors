import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class UserCatalog {

	private static final String DATAPATH = "serverData/userCatalogData";
	
	private Map<String,MequieUser> users;
	private File data;

	public UserCatalog() throws FileNotFoundException {
		this.users = new HashMap<>();
		this.data = new File(DATAPATH);
		String user = "ADMIN";
		if(!this.data.exists()) {
			this.data.getParentFile().mkdirs();
			MequieUser u = new MequieUser(user);
			u.login();
			this.users.put(user, u);
		}else {
			loadData();
			MequieUser u = this.users.get(user);
			u.login();
			this.users.replace(user, u);
		}
		this.update();
	}

	public void create(String user) {
		MequieUser u = new MequieUser(user);
		this.users.put(user, u);
		this.update();
	}
	
	public boolean login(String user) {
		MequieUser u = this.users.get(user);
		if(!u.isOnline()) {
			u.login();
			this.users.replace(user, u);
			return true;
		}else {
			return false;
		}
	}
	
	public void logout(String user) {
		MequieUser u = this.users.get(user);
		u.logout();
		this.users.replace(user, u);
	}

	public String info(String user) {
		return this.users.get(user).info();
	}

	public void createGroup(String user, String groupID) {
		MequieUser u = this.users.get(user);
		u.createGroup(groupID);
		this.users.replace(user, u);
		this.update();
	}

	public void joinGroup(String user, String groupID) {
		MequieUser u = this.users.get(user);
		u.joinGroup(groupID);
		this.users.replace(user, u);
		this.update();
	}

	public void leaveGroup(String user, String groupID) {
		MequieUser u = this.users.get(user);
		u.leaveGroup(groupID);
		this.users.replace(user, u);
		this.update();
	}

	public boolean exists(String user) {
		return this.users.containsKey(user);
	}
	
	private void loadData() throws FileNotFoundException {
		Scanner sc = new Scanner(this.data);
		String user;
		String[] information;
		while(sc.hasNextLine()) {
			user = sc.nextLine().split(" ")[0];
			information = user.split("/");
			MequieUser loadedUser = new MequieUser(information);
			this.users.put(information[0], loadedUser);
		}
		sc.close();	
	}
	
	private void update() {
		try {
			Writer w = new FileWriter(this.data);
			for(MequieUser user : this.users.values()) {
				w.write(user.info() + " \n");
			}
			w.close();
		} catch (IOException e) {
			System.out.println("Erro ao guardar estado do catalogo dos utilizadores");
		}
	}

}
