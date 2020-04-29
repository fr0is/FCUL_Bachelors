import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class MequieUser implements Serializable{

	private static final long serialVersionUID = -7464885917560158850L;
	
	private String name;
	private boolean isOnline;
	private List<String> ownedGroups;
	private List<String> groups;

	public MequieUser(String name) {
		this.name = name;
		this.isOnline = false;
		this.ownedGroups = new ArrayList<>();
		this.groups = new ArrayList<>();
	}

	public void createGroup(String groupID) {
		this.ownedGroups.add(groupID);
		this.groups.add(groupID);
	}

	public void joinGroup(String groupID) {
		this.groups.add(groupID);
	}

	public void leaveGroup(String groupID) {
		this.groups.remove(groupID);
	}

	public String getName() {
		return this.name;
	}

	public boolean isOnline() {
		return this.isOnline;
	}

	public void login() {
		this.isOnline = true;
	}

	public void logout() {
		this.isOnline = false;
	}

	public String info() {
		StringBuilder info = new StringBuilder(this.name + "/");
		if(!this.ownedGroups.isEmpty()) {
			for(String owned : this.ownedGroups) 
				info.append(owned + ":");
			info.replace(info.length()-1, info.length(),"/");
		}else {
			info.append("EMPTY/");
		}
		if(!this.groups.isEmpty()) {
			for(String on : this.groups) 
				info.append(on + ":");
			info.delete(info.length()-1, info.length());
		}else {
			info.append("EMPTY");
		}
		return info.toString();
	}

}
