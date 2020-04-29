import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class UserCatalog {

	private static final String DATAPATH = "serverData/userCatalogData";

	private HashMap<String,MequieUser> users;
	private File data;
	private SecretKey infoKey;

	public UserCatalog(SecretKey infoKey) throws FileNotFoundException {
		this.infoKey = infoKey;
		this.users = new HashMap<>();
		this.data = new File(DATAPATH);
		String user = "ADMIN";
		if(!this.data.exists()) {
			this.data.getParentFile().mkdirs();
			try {
				this.data.createNewFile();
			} catch (IOException e) {
				System.out.println("Erro ao gerar ficheiro para o Catalogo de utilizadores");
			}
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
		if(!user.equals("ADMIN")) {
			MequieUser u = this.users.get(user);
			u.logout();
			this.users.replace(user, u);
		}
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

	@SuppressWarnings("unchecked")
	private void loadData() throws FileNotFoundException {
		String decryptedPath = null;
		try {
			decryptedPath = FileDecrypter.decryptFile(this.data, this.infoKey);

			FileInputStream fis = new FileInputStream(decryptedPath);
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.users = (HashMap<String, MequieUser>) ois.readObject();
			ois.close();
			fis.close();

			for(String u : this.users.keySet())
				this.logout(u);

			FileDecrypter.encryptFile(decryptedPath, this.data, this.infoKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException | ClassNotFoundException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println("Erro ao carregar os dados do catalogo dos utilizadores");
		}
	}

	private void update() {
		String decryptedPath = null;
		try {
			decryptedPath = FileDecrypter.decryptFile(this.data, this.infoKey);

			FileOutputStream fos = new FileOutputStream(decryptedPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.users);
			oos.close();
			fos.close();

			FileDecrypter.encryptFile(decryptedPath, this.data, this.infoKey);
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println("Erro ao guardar estado do catalogo dos utilizadores");
			e.printStackTrace();
		}
	}

}
