import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


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
	private SecretKey infoKey;

	public GroupCatalog(UserCatalog uc, SecretKey infoKey) throws FileNotFoundException{
		this.infoKey = infoKey;
		this.uc = uc;
		this.groups = new HashMap<>();
		this.data = new File(DATAPATH);
		String user = "ADMIN";
		if(!this.data.exists()) {
			String geral = "Geral";
			this.data.getParentFile().mkdirs();
			try {
				this.data.createNewFile();
			} catch (IOException e1) {
				System.out.println("Erro ao criar o ficheiro de dados dos grupos");
			}
			MequieGroup group;
			try {
				group = new MequieGroup(geral,user,null,this.infoKey);
			} catch (MequieException e) {
				group = null;
				System.out.println("O grupo geral nao foi criado com sucesso: " + e.getMessage());
			}
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

	public void create(String user, String groupID, Map<String, EncryptedKey> groupKey) throws MequieException{
		MequieGroup group = new MequieGroup(groupID,user,groupKey,infoKey);
		uc.createGroup(user,groupID);
		this.groups.put(groupID, group);
		update();
	}

	public boolean addUser(String targetUser, String groupID, Map<String, EncryptedKey> groupKey) throws MequieException {
		MequieGroup group = this.groups.get(groupID);
		if(!this.isUserIn(targetUser,groupID)) {
			group.addUser(targetUser,groupKey, this.infoKey);
			group.update(this.infoKey);
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

	public int getCurrentKeyIndex(String groupID) {
		return this.groups.get(groupID).getCurrentKeyIndex();
	}

	public String[] getMembersList(String groupID) {
		return this.groups.get(groupID).getMembersList();
	}

	public HashMap<Integer,EncryptedKey> getGroupKeys(String groupID, String user) throws MequieException{
		return this.groups.get(groupID).getGroupKeys(user, this.infoKey);
	}

	public EncryptedKey getCurrentKey(String groupID, String user) throws MequieException{
		return this.groups.get(groupID).getCurrentKey(user, this.infoKey);
	}

	public boolean removeUser(String targetUser, String groupID, Map<String, EncryptedKey> groupKey) throws MequieException {
		MequieGroup group = this.groups.get(groupID);
		if(this.isUserIn(targetUser,groupID)) {
			group.removeUser(targetUser, groupKey, infoKey);
			group.update(this.infoKey);
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
		String decryptedPath = null;

		try {
			decryptedPath = FileDecrypter.decryptFile(this.data, infoKey);
			Scanner sc = new Scanner(new File(decryptedPath));
			String group;
			String[] information;
			while(sc.hasNextLine()) {
				group = sc.nextLine().split(" ")[0];
				information = group.split("/");
				MequieGroup loadedGroup;
				loadedGroup = new MequieGroup(information,this.infoKey);
				this.groups.put(information[0], loadedGroup);
			}
			sc.close();

			FileDecrypter.encryptFile(decryptedPath, this.data, infoKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println("Erro ao carregar os dados do catalogo de grupos");
		}
	}

	private void update() {
		String decryptedPath = null;
		try {
			decryptedPath = FileDecrypter.decryptFile(this.data, infoKey);
			Writer w = new FileWriter(new File(decryptedPath));
			for(MequieGroup group : this.groups.values()) {
				w.write(group.dataInfo() + " \n");
			}
			w.close();
			FileDecrypter.encryptFile(decryptedPath, this.data, infoKey);
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println("Erro ao guardar estado do catalogo dos grupos");
		}
	}

	public void sendMsg(Message message, String groupID) {
		MequieGroup g = this.groups.get(groupID);
		g.sendToInbox(message);
		g.update(this.infoKey);
		this.groups.replace(groupID, g);
	}

	public LinkedList<Message> collect(String user, String groupID, Set<Integer> keyIndexs) {
		MequieGroup g = this.groups.get(groupID);
		LinkedList<Message> msgs = g.collect(user,keyIndexs);
		g.update(this.infoKey);
		this.groups.replace(groupID, g);
		return msgs;
	}

	public LinkedList<Message> history(String groupID, Set<Integer> keyIndexs) {
		MequieGroup g = this.groups.get(groupID);
		return g.history(keyIndexs);
	}

	public void checkNupdateInbox(String groupID) {
		MequieGroup g = this.groups.get(groupID);
		g.checkForReadedMsgs();
		g.update(this.infoKey);
		this.groups.replace(groupID, g);
	}

}
