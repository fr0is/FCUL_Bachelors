import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

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
	private HashMap<String,String> users;
	private LinkedList<Message> msgs;
	private LinkedList<Message> history;
	private HashMap<String,byte[]> photos;
	private File userData;
	private File inbox;
	private File historyFile;
	private File photoBox;
	private int keyIndex;

	public MequieGroup(String id, String owner, Map<String, EncryptedKey> groupKey, SecretKey infoKey) throws MequieException{
		this.keyIndex = 0;
		this.id = id;
		this.owner = owner;
		this.users = new HashMap<>();
		this.msgs = new LinkedList<>();
		this.history = new LinkedList<>();
		this.photos = new HashMap<>();
		String keys = this.createKeyFile(owner,infoKey);
		this.users.put(owner,keys);

		this.createDataFiles();
		this.giveKeys(groupKey,infoKey);
		this.update(infoKey);
	}

	public MequieGroup(String[] information,SecretKey infoKey){
		this.id = information[0];
		this.owner = information[1];
		this.keyIndex = Integer.parseInt(information[2]);
		this.users = new HashMap<>();
		this.msgs = new LinkedList<>();
		this.history = new LinkedList<>();
		this.photos = new HashMap<>();

		this.userData = new File(GROUPPATH + "/" + this.id + "/userData");
		this.inbox = new File(GROUPPATH + "/" + this.id + "/inbox");
		this.historyFile  = new File(GROUPPATH + "/" + this.id + "/history");
		this.photoBox = new File(GROUPPATH + "/" + this.id + "/photoBox");

		this.loadGroupState(infoKey);
	}

	public String getId() {
		return this.id;
	}

	public boolean isOwner(String user) {
		return user.equals(this.owner);
	}

	public int getCurrentKeyIndex() {
		return this.keyIndex;
	}

	public void addUser(String targetUser, Map<String, EncryptedKey> groupKey, SecretKey infoKey) throws MequieException {
		this.keyIndex++;
		this.users.put(targetUser,this.createKeyFile(targetUser, infoKey));
		this.giveKeys(groupKey, infoKey);
	}

	public boolean isUserIn(String targetUser) {
		return this.users.keySet().contains(targetUser);
	}

	public String[] getMembersList() {
		String[] userArray = new String[this.users.size()];
		return this.users.keySet().toArray(userArray);
	}

	public void removeUser(String targetUser, Map<String, EncryptedKey> groupKey, SecretKey infoKey) throws MequieException {
		LinkedList<Message> temp = new LinkedList<>();
		for(Message m : this.msgs) {
			m.read(targetUser);
			temp.add(m);
		}
		this.msgs = temp;
		this.checkForReadedMsgs();
		this.keyIndex++;
		File del = new File(this.users.remove(targetUser));
		del.delete();
		this.giveKeys(groupKey, infoKey);
	}

	public String dataInfo() {
		return this.id + "/" + this.owner + "/" + this.keyIndex;
	}

	public String info(String user) {
		StringBuilder info = new StringBuilder(this.owner + " " + this.users.size());
		if(this.isOwner(user)) 
			for(String u : this.users.keySet())
				info.append(" " + u);
		return info.toString();
	}

	public void sendToInbox(Message message) {
		for(String u : this.users.keySet())
			message.sendTo(u);
		this.msgs.add(message);
		if(!message.isText())
			this.photos.put(((PhotoMessage) message).getPhotoName(), message.getContent());
		this.checkForReadedMsgs();
	}

	public void checkForReadedMsgs() {
		List<String> pics = new ArrayList<>();
		while(this.msgs.peek() != null && this.msgs.peek().everyoneSaw()) {
			Message m = this.msgs.remove();
			if(m.isText()) {
				this.history.add(m);
			}else {
				pics.add(((PhotoMessage) m).getPhotoName());
			}
		}
		checkPictures(pics);
	}

	private void checkPictures(List<String> pics) {
		for(String p : pics) {
			boolean Found = false;
			for(Message m : this.msgs) {
				if(!m.isText() && p.equals(((PhotoMessage) m).getPhotoName()))
					Found = true;
			}
			if(!Found) {
				this.photos.remove(p);
			}
		}
	}

	public LinkedList<Message> history(Set<Integer> keyIndexs){
		LinkedList<Message> hist = new LinkedList<>();
		for(Message m : this.history) {
			if(keyIndexs.contains(m.getKeyIndex())) {
				hist.add(m);
			}
		}
		return (hist.isEmpty())?null:hist;
	}

	public LinkedList<Message> collect(String user, Set<Integer> keyIndexs){
		LinkedList<Message> collected = new LinkedList<>();
		LinkedList<Message> temp = new LinkedList<>();
		for(Message m : this.msgs) {
			if(m.wasNotSeenBy(user) && keyIndexs.contains(m.getKeyIndex())) {
				m.read(user);
				collected.add(m);
			}
			temp.add(m);
		}
		this.msgs = temp;
		this.checkForReadedMsgs();
		return (collected.isEmpty())?null:collected;
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer,EncryptedKey> getGroupKeys(String user, SecretKey infoKey) throws MequieException{
		String decryptedPath = null;
		File f = new File(this.users.get(user));
		try {
			decryptedPath = FileDecrypter.decryptFile(f, infoKey);
			FileInputStream fis = new FileInputStream(decryptedPath);
			ObjectInputStream oiso = new ObjectInputStream(fis);

			HashMap<Integer,EncryptedKey> key = (HashMap<Integer,EncryptedKey>) oiso.readObject();

			oiso.close();
			fis.close();
			FileDecrypter.encryptFile(decryptedPath, f, infoKey);
			return key;
		} catch (IOException | ClassNotFoundException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			throw new MequieException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public EncryptedKey getCurrentKey(String user, SecretKey infoKey) throws MequieException {
		String decryptedPath = null;
		File f = new File(this.users.get(user));
		try {
			decryptedPath = FileDecrypter.decryptFile(f, infoKey);
			FileInputStream fis = new FileInputStream(decryptedPath);
			ObjectInputStream oiso = new ObjectInputStream(fis);

			HashMap<Integer,EncryptedKey> keys = (HashMap<Integer,EncryptedKey>) oiso.readObject();
			EncryptedKey key = keys.get(this.keyIndex);
			if(key == null) {
				oiso.close();
				fis.close();
				throw new MequieException("A chave nao existe");
			}

			oiso.close();
			fis.close();
			FileDecrypter.encryptFile(decryptedPath, f, infoKey);
			return key;
		} catch (MequieException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException | ClassNotFoundException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			throw new MequieException(e);
		}

	}

	private String createKeyFile(String user, SecretKey infoKey) throws MequieException {
		String decryptedPath = null;
		try {
			File key = new File(GROUPPATH + "/" + this.id + "/keys/" + user);
			key.getParentFile().mkdirs();
			key.createNewFile();
			decryptedPath = FileDecrypter.decryptFile(key, infoKey);
			HashMap<Integer,EncryptedKey> m = new HashMap<>();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(decryptedPath));
			oos.writeObject(m);
			oos.close();
			FileDecrypter.encryptFile(decryptedPath, key, infoKey);
			return GROUPPATH + "/" + this.id + "/keys/" + user;
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			throw new MequieException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void giveKeys(Map<String, EncryptedKey> groupKey, SecretKey infoKey) throws MequieException {
		if(groupKey != null) {
			String decryptedPath = null;
			try {
				HashMap<Integer,EncryptedKey> m;
				for(Map.Entry<String,EncryptedKey> entry : groupKey.entrySet()) {
					String user = entry.getKey();
					EncryptedKey key = entry.getValue();
					File keys = new File(this.users.get(user));

					decryptedPath = FileDecrypter.decryptFile(keys, infoKey);

					FileInputStream fis = new FileInputStream(decryptedPath);
					ObjectInputStream oiso = new ObjectInputStream(fis);
					m = (HashMap<Integer, EncryptedKey>) oiso.readObject();
					oiso.close();
					fis.close();

					m.put(key.getIndex(), key);

					FileOutputStream fos = new FileOutputStream(decryptedPath);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(m);
					oos.close();
					fos.close();

					FileDecrypter.encryptFile(decryptedPath, keys, infoKey);
				}
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException | ClassNotFoundException e) {
				FileDecrypter.deleteTemp(decryptedPath);
				throw new MequieException(e);
			}
		}
	}

	private void createDataFiles() {
		this.userData = new File(GROUPPATH + "/" + this.id + "/userData");
		if(!this.userData.exists()) {
			this.userData.getParentFile().mkdirs();
			try {
				this.userData.createNewFile();
			} catch (IOException e) {
				System.out.println(id + ": Erro ao gerar ficheiro de dados dos utilizadores");
			}
		}

		this.inbox = new File(GROUPPATH + "/" + this.id + "/inbox");
		if(!this.inbox.exists()) {
			this.inbox.getParentFile().mkdirs();
			try {
				this.inbox.createNewFile();
			} catch (IOException e) {
				System.out.println(id + ": Erro ao gerar ficheiro da caixa de entrada");
			}
		}

		this.historyFile  = new File(GROUPPATH + "/" + this.id + "/history");
		if(!this.historyFile.exists()) {
			this.historyFile.getParentFile().mkdirs();
			try {
				this.historyFile.createNewFile();
			} catch (IOException e) {
				System.out.println(id + ": Erro ao gerar ficheiro do historico");
			}
		}

		this.photoBox = new File(GROUPPATH + "/" + this.id + "/photoBox");
		if(!this.photoBox.exists()) {
			this.photoBox.getParentFile().mkdirs();
			try {
				this.photoBox.createNewFile();
			} catch (IOException e) {
				System.out.println(id + ": Erro ao gerar caixa de fotos");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void loadGroupState(SecretKey infoKey) {
		String decryptedPath = null;
		try {
			//-------------------userData-----------------------------
			decryptedPath = FileDecrypter.decryptFile(this.userData, infoKey);

			FileInputStream fis1 = new FileInputStream(decryptedPath);
			ObjectInputStream ois1 = new ObjectInputStream(fis1);
			this.users = (HashMap<String,String>) ois1.readObject();
			ois1.close();
			fis1.close();

			FileDecrypter.encryptFile(decryptedPath, this.userData, infoKey);
			//-------------------inbox--------------------------------
			decryptedPath = FileDecrypter.decryptFile(this.inbox, infoKey);

			FileInputStream fis2 = new FileInputStream(decryptedPath);
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			this.msgs = (LinkedList<Message>) ois2.readObject();
			ois2.close();
			fis2.close();

			FileDecrypter.encryptFile(decryptedPath, this.inbox, infoKey);
			//------------------history--------------------------------
			decryptedPath = FileDecrypter.decryptFile(this.historyFile, infoKey);

			FileInputStream fis3 = new FileInputStream(decryptedPath);
			ObjectInputStream ois3 = new ObjectInputStream(fis3);
			this.history = (LinkedList<Message>) ois3.readObject();
			ois3.close();
			fis3.close();

			FileDecrypter.encryptFile(decryptedPath, this.historyFile, infoKey);
			//------------------photoBox-------------------------------
			decryptedPath = FileDecrypter.decryptFile(this.photoBox, infoKey);

			FileInputStream fis4 = new FileInputStream(decryptedPath);
			ObjectInputStream ois4 = new ObjectInputStream(fis4);
			this.photos = (HashMap<String,byte[]>) ois4.readObject();
			ois4.close();
			fis4.close();

			FileDecrypter.encryptFile(decryptedPath, this.photoBox, infoKey);

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IOException | ClassNotFoundException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println(this.id + ": Erro ao carregar os dados deste grupo");
		}
	}

	public void update(SecretKey infoKey) {
		this.updateInbox(infoKey);
		this.updateUserList(infoKey);
		this.updateHistory(infoKey);
		this.updatePhotoBox(infoKey);
	}

	private void updateUserList(SecretKey infoKey) {
		String decryptedPath = null;
		try {
			decryptedPath = FileDecrypter.decryptFile(this.userData, infoKey);

			FileOutputStream fos = new FileOutputStream(decryptedPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.users);
			oos.close();
			fos.close();

			FileDecrypter.encryptFile(decryptedPath, this.userData, infoKey);
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println(this.id + ": Erro ao atualizar a lista de utilizadores");
		}
	}

	private void updateInbox(SecretKey infoKey) {
		String decryptedPath = null;
		try {
			decryptedPath = FileDecrypter.decryptFile(this.inbox, infoKey);

			FileOutputStream fos = new FileOutputStream(decryptedPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.msgs);
			oos.close();
			fos.close();

			FileDecrypter.encryptFile(decryptedPath, this.inbox, infoKey);
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println(this.id + ": Erro ao atualizar a caixa de entrada deste grupo");
		}
	}

	private void updateHistory(SecretKey infoKey) {
		String decryptedPath = null;
		try {
			decryptedPath = FileDecrypter.decryptFile(this.historyFile, infoKey);

			FileOutputStream fos = new FileOutputStream(decryptedPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.history);
			oos.close();
			fos.close();

			FileDecrypter.encryptFile(decryptedPath, this.historyFile, infoKey);
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println(this.id + ": Erro ao atualizar o historico deste grupo");
		}
	}

	private void updatePhotoBox(SecretKey infoKey) {
		String decryptedPath = null;
		try {
			decryptedPath = FileDecrypter.decryptFile(this.photoBox, infoKey);

			FileOutputStream fos = new FileOutputStream(decryptedPath);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.photos);
			oos.close();
			fos.close();

			FileDecrypter.encryptFile(decryptedPath, this.photoBox, infoKey);
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			FileDecrypter.deleteTemp(decryptedPath);
			System.out.println(this.id + ": Erro ao atualizar a caixa de fotos deste grupo");
		}
	}
}
