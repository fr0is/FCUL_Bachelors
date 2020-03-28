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

public class GroupCatalog {

	private static final String DATAPATH = "serverData/groupCatalogData/allGroupsData";

	private UserCatalog uc;
	private Map<String,MequieGroup> groups;
	private File data;

	public GroupCatalog(UserCatalog uc) throws FileNotFoundException {
		this.uc = uc;
		this.groups = new HashMap<>();
		this.data = new File(DATAPATH);
		String user = "ADMIN";
		if(!this.data.exists()) {
			String geral = "Geral";
			this.data.getParentFile().mkdirs();
			MequieGroup group = new MequieGroup(geral,user);
			uc.createGroup(user,geral);
			this.groups.put(geral, group);
			update();
		}else {
			loadData();
		}
	}
	
	public boolean exist(String groupID) {
		return this.groups.containsKey(groupID);
	}

	public void create(String user, String groupID) {
		MequieGroup group = new MequieGroup(groupID,user);
		uc.createGroup(user,groupID);
		this.groups.put(groupID, group);
		update();
	}

	public boolean addUser(String targetUser, String groupID) {
		MequieGroup group = this.groups.get(groupID);
		if(!this.isUserIn(targetUser,groupID)) {
			group.addUser(targetUser);
			uc.joinGroup(targetUser,groupID);
			this.groups.replace(groupID,group);
			update();
			return true;
		}else {
			return false;
		}
	}

	public boolean owns(String user, String groupID) {
		return this.groups.get(groupID).isOwner(user);
	}

	public boolean isUserIn(String targetUser, String groupID) {
		return this.groups.get(groupID).isUserIn(targetUser);
	}

	public boolean removeUser(String targetUser, String groupID) {
		MequieGroup group = this.groups.get(groupID);
		if(this.isUserIn(targetUser,groupID)) {
			group.removeUser(targetUser);
			uc.leaveGroup(targetUser,groupID);
			this.groups.replace(groupID,group);
			update();
			return true;
		}else {
			return false;
		}
	}

	public String info(String user, String groupID) {
		return this.groups.get(groupID).info(user);
	}

	private void loadData() throws FileNotFoundException {
		Scanner sc = new Scanner(this.data);
		String group;
		String[] information;
		while(sc.hasNextLine()) {
			group = sc.nextLine().split(" ")[0];
			information = group.split("/");
			MequieGroup loadedGroup = new MequieGroup(information);
			this.groups.put(information[0], loadedGroup);
		}
		sc.close();
	}

	private void update() {
		try {
			Writer w = new FileWriter(this.data);
			for(MequieGroup group : this.groups.values()) {
				w.write(group.dataInfo() + " \n");
			}
			w.close();
		} catch (IOException e) {
			System.out.println("Erro ao guardar estado do catalogo dos grupos");
		}
	}

	public void sendMsg(Message message, String groupID) {
		MequieGroup g = this.groups.get(groupID);
		g.sendToInbox(message);
		this.groups.replace(groupID, g);
	}

	public String[] collect(String user, String groupID) {
		MequieGroup g = this.groups.get(groupID);
		String[] msgs = g.collect(user);
		this.groups.replace(groupID, g);
		return msgs;
	}
	
	public File history(String groupID) {
		MequieGroup g = this.groups.get(groupID);
		return g.history();
	}

	public void checkNupdateInbox(String groupID) {
		MequieGroup g = this.groups.get(groupID);
		g.checkForReadedMsgs();
		g.updateInbox();
		this.groups.replace(groupID, g);
	
	}

}
