import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class MequieClientHandler {

	private static final String SEPARATOR = " ";
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String path;
	private KeyStore kstore;
	private String ksPassword;
	private String user;

	public MequieClientHandler(String user, ObjectInputStream in, ObjectOutputStream out, KeyStore kstore, String ksPassword) throws SocketException {
		this.user = user;
		this.in = in;
		this.out = out;
		this.path = "clientData/"+user+"/photoBox";
		File p = new File(this.path);
		this.kstore = kstore;
		this.ksPassword = ksPassword;
		if(!p.exists())
			p.mkdirs();
		displayCommands();
	}

	public void create(String groupID) throws SocketException {
		try {
			String[] users = new String[1];
			users[0] = this.user;
			HashMap<String,EncryptedKey> groupKey = this.createGroupKey(users,null, 0);
			out.writeObject("100"+SEPARATOR+groupID);
			out.writeObject(groupKey);
			String answer = (String) in.readObject();
			switch(answer) {
			case "101":
				System.out.println("Criaste o grupo " + groupID);
				break;
			case "102":
				System.out.println("O grupo " + groupID + " ja existe");
				break;
			case "103":
				System.out.println("Argumentos em falta");
				break;
			default:
				throw new IOException();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Houve um erro ao criar grupo");
		} catch (MequieException e) {
			System.out.println("Houve um erro ao criar a chave para o grupo");
		}
	}

	public void addu(String userID, String groupID) throws SocketException {
		try {
			out.writeObject("200"+SEPARATOR+userID+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "201":
				int keyIndex = (int) in.readObject();
				String[] members = (String[]) in.readObject();
				try {
					HashMap<String,EncryptedKey> groupKey = this.createGroupKey(members, userID, keyIndex+1);
					out.writeObject(true);
					out.writeObject(groupKey);
					if((boolean) in.readObject()) {
						System.out.println("Adicionaste " + userID + " ao grupo " + groupID);
					}else {
						System.out.println("A operacao de adicao nao foi concluida com sucesso");
					}
				} catch (Exception e) {
					out.writeObject(false);
					System.out.println("Erro ao adicionar o utilizador indicado: " + e.getMessage());
				}
				break;
			case "202":
				System.out.println("O grupo " + groupID + " nao existe");
				break;
			case "203":
				System.out.println("Nao es dono do grupo " + groupID);
				break;
			case "204":
				System.out.println("O utilizador nao existe");
				break;
			case "205":
				System.out.println(userID + " ja se encontra dentro deste grupo");
				break;
			default:
				throw new IOException();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao adicionar o utilizador indicado");
		}
	}

	public void removeu(String userID, String groupID) throws SocketException {
		try {
			out.writeObject("300"+SEPARATOR+userID+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "301":
				int keyIndex = (int) in.readObject();
				String[] members = (String[]) in.readObject();
				try {
					HashMap<String,EncryptedKey> groupKey = this.createGroupKey(members, null, keyIndex+1);
					out.writeObject(true);
					out.writeObject(groupKey);
					if((boolean) in.readObject()) {
						System.out.println(userID + " removido do grupo " + groupID);
					}else {
						System.out.println("A operacao de remocao nao foi concluida com sucesso");
					}
				}catch(Exception e) {
					out.writeObject(false);
					System.out.println("Erro ao remover o utilizador indicado :" + e.getMessage());
				}
				break;
			case "302":
				System.out.println("O grupo " + groupID + " nao existe");
				break;
			case "303":
				System.out.println("Nao es dono do grupo " + groupID);
				break;
			case "304":
				System.out.println("Nao te podes remover do teu proprio grupo visto que es o dono");
				break;
			case "305":
				System.out.println("O utilizador nao existe");
				break;
			case "306":
				System.out.println(userID + " nao pertence ao grupo " + groupID);
				break;
			default:
				throw new IOException();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao remover o utilizador indicado");
		}
	}

	public void ginfo(String groupID) throws SocketException {
		try {
			out.writeObject("400"+SEPARATOR+groupID);
			String incoming = (String) in.readObject();
			//String[] answer = incoming.split(" ");
			switch(incoming) {
			case "401":
				String data = (String) in.readObject();
				displayGinfo(groupID,data.split(" "));
				break;
			case "402":
				System.out.println("O grupo " + groupID + " nao existe");
				break;
			default:
				throw new IOException();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao obter informacao do grupo");
		}
	}

	public void uinfo() throws SocketException {
		try {
			out.writeObject("500");
			String incoming = (String) in.readObject();
			if(incoming.equals("501")) {
				String data = (String) in.readObject();
				displayUinfo(data);
			}else {
				throw new IOException();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao obter a sua informacao");
		}
	}

	public void msg(String groupID, String msg) throws SocketException {
		try {
			out.writeObject("600"+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "601":
				if((boolean) in.readObject()) {
					EncryptedKey key = (EncryptedKey) in.readObject();
					try {
						TextMessage tm = this.EncryptedTextMessage(key, msg);
						out.writeObject(true);
						out.writeObject(tm);
						System.out.println("Mensagem enviada para o grupo " + groupID);
					} catch (MequieException e) {
						out.writeObject(false);
						System.out.println("Erro ao encriptar a mensagem");
					}
				}else {
					System.out.println("Houve um erro ao adquirir a chave mais recente do grupo");
				}
				break;
			case "602":
				System.out.println("O grupo " + groupID + " nao existe");
				break;
			case "603":
				System.out.println("Nao pertences ao grupo " + groupID);
				break;
			default:
				throw new IOException();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao enviar a mensagem");
		}
	}

	private TextMessage EncryptedTextMessage(EncryptedKey key, String msg) throws MequieException {
		try {
			Cipher c = Cipher.getInstance("RSA");
			c.init(Cipher.UNWRAP_MODE, this.getUserPrivateKey());
			SecretKey unwrappedKey = (SecretKey) c.unwrap(key.getEncryptedKey(), "AES", Cipher.SECRET_KEY);

			Cipher cc = Cipher.getInstance("AES");
			cc.init(Cipher.ENCRYPT_MODE, unwrappedKey);
			return new TextMessage(this.user,cc.doFinal(msg.getBytes()),key.getIndex());
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | MequieException | IllegalBlockSizeException | BadPaddingException e) {
			throw new MequieException(e);
		}
	}

	public void photo(String groupID, String photo) throws SocketException {
		File pic = new File(photo);
		if(pic.exists()) {
			try {
				out.writeObject("700"+SEPARATOR+groupID);
				String answer = (String) in.readObject();
				switch(answer) {
				case "701":
					if((boolean) in.readObject()) {
						EncryptedKey key = (EncryptedKey) in.readObject();
						try {
							PhotoMessage pm = this.EncryptedPhotoMessage(key, pic);
							out.writeObject(true);
							out.writeObject(pm);
							System.out.println("Fotografia enviada para o grupo " + groupID);
						} catch (MequieException e) {
							out.writeObject(false);
							System.out.println("Erro ao encriptar a foto");
						}
					}else {
						System.out.println("Houve um erro ao adquirir a chave mais recente do grupo");
					}
					break;
				case "702":
					System.out.println("O grupo " + groupID + " nao existe");
					break;
				case "703":
					System.out.println("Nao pertences ao grupo " + groupID);
					break;
				default:
					throw new IOException();
				}
			}catch (ClassNotFoundException e) {
				System.out.println("Erro ao receber a resposta do servidor");
			}catch (SocketException e) {
				throw new SocketException();
			}catch (IOException e) {
				System.out.println("Erro ao enviar a foto");
			}
		}else {
			System.out.println("A fotografia nao foi encontrada ou nao existe");
		}
	}

	private PhotoMessage EncryptedPhotoMessage(EncryptedKey key, File photo) throws MequieException {
		try {
			byte[] content = Files.readAllBytes(photo.toPath());

			Cipher c = Cipher.getInstance("RSA");
			c.init(Cipher.UNWRAP_MODE, this.getUserPrivateKey());
			SecretKey unwrappedKey = (SecretKey) c.unwrap(key.getEncryptedKey(), "AES", Cipher.SECRET_KEY);

			Cipher cc = Cipher.getInstance("AES");
			cc.init(Cipher.ENCRYPT_MODE, unwrappedKey);

			return new PhotoMessage(this.user,cc.doFinal(content),photo.getName(), key.getIndex());
		} catch (NoSuchAlgorithmException | IOException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new MequieException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void collect(String groupID) throws SocketException {
		try {
			out.writeObject("800"+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "801":
				if((boolean) in.readObject()) {
					if((boolean) in.readObject()) {
						HashMap<Integer,EncryptedKey> groupKeys = (HashMap<Integer, EncryptedKey>) in.readObject();
						LinkedList<Message> msgs = (LinkedList<Message>) in.readObject();
						try {
							System.out.println("Mensagens do grupo " + groupID);
							processMessages(msgs,groupKeys);
						} catch (MequieException e) {
							System.out.println("Houve um erro ao processar as mensagens vindas do servidor: " + e.getMessage());
						}
						System.out.println();
					}else {
						System.out.println("De momento nao tens mensagens por ver neste grupo");
					}
				}else {
					System.out.println("Houve um erro no servidor ao adquirir as suas mensagens nao visualizadas");
				}
				break;
			case "802":
				System.out.println("De momento ja viste todas as mensagens do grupo " + groupID);
				break;
			case "803":
				System.out.println("O grupo " + groupID + " nao existe");
				break;
			case "804":
				System.out.println("Nao pertences ao grupo " + groupID);
				break;
			default:
				throw new IOException();
			}

		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao receber mensagens nao vistas");
		}
	}

	@SuppressWarnings("unchecked")
	public void history(String groupID) throws SocketException {
		try {
			out.writeObject("900"+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "901":
				if((boolean) in.readObject()) {
					if((boolean) in.readObject()) {
						HashMap<Integer,EncryptedKey> groupKeys = (HashMap<Integer, EncryptedKey>) in.readObject();
						LinkedList<Message> history = (LinkedList<Message>) in.readObject();
						try {
							processHistory(groupID, history, groupKeys);
						} catch (MequieException e) {
							System.out.println("Houve um erro ao processar o historico :" + e.getMessage());
						}
					}else {
						System.out.println("Nao existe historico neste grupo ainda");
					}
				}else {
					System.out.println("Houve um erro no servidor ao adquirir o historico deste grupo");
				}
				break;
			case "902":
				System.out.println("O grupo " + groupID + " nao existe");
				break;
			case "903":
				System.out.println("Nao pertences ao grupo " + groupID);
				break;
			case "904":
				System.out.println("O grupo " + groupID + " ainda nao tem historico");
				break;
			default:
				throw new IOException();
			}
		}catch (ClassNotFoundException e) {
			System.out.println("Erro ao receber a resposta do servidor");
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao obter o historico do grupo");
		}
	}

	public void displayCommands() {
		Object[][] commands = {
				{"create <groupID>", "Cria uma nova conversa de grupo."},
				{"addu <userID> <groupID>", "adiciona o utilizador userID ao grupo indicado."},
				{"removeu <userID> <groupID>", "remove o utilizador userID do grupo indicado."},
				{"ginfo <groupID>", "mostra a informacao do grupo indicado,"},
				{"uinfo", "mostra a informacao do utilizador."},
				{"msg <groupID> <msg>", "envia a mensagem msg para o groupID."},
				{"photo <groupID> <photo>", "envia a fotografia photo para o groupID."},
				{"collect <groupID>", "recebe do grupo indicado todas as mensagens e fotografias ainda nao visualizadas."},
				{"history <groupID>", "mostra o historico das mensagens do grupo indicado."},
				{"commands", "Apresenta a lista dos comandos disponiveis."},
				{"logout", "Termina a sessao  e o Mequie"}
		};

		String commandFormat = "%1$-28s ::";
		String descFormat = "  %2$s\n\n";
		String format = commandFormat.concat(descFormat);

		System.out.println();
		System.out.println("---------------------------------- COMANDOS ----------------------------------");
		for(Object[] cmd : commands) {
			System.out.printf(format, cmd[0], cmd[1]);
		}
		System.out.println("------------------------------------------------------------------------------");
		System.out.println();
	}

	public boolean logout() throws SocketException {
		try {
			out.writeObject("000");
			System.out.println("Terminaste a sessao");
			return true;
		}catch (SocketException e) {
			throw new SocketException();
		}catch (IOException e) {
			System.out.println("Erro ao terminar a sessao");
			return false;
		}
	}

	private void displayGinfo(String groupID, String[] answer) {
		System.out.println("-----------------------------");
		System.out.println("Nome do grupo: " + groupID);
		System.out.println("Dono: " + answer[0]);
		System.out.println("Numero de membros neste grupo: " + answer[1]);
		if(answer.length > 2) {
			System.out.println("-----Membros neste grupo-----");
			for(int i = 2; i < answer.length; i++)
				System.out.println("- \t" + answer[i]);
		}
		System.out.println("-----------------------------");
	}

	private void displayUinfo(String answer) {
		String[] params = answer.split("/");
		System.out.println("-------A tua informacao------");
		System.out.println("Nome: " + params[0]);
		System.out.println("----Grupos de que es dono----");
		if(params[1].equals("EMPTY")) {
			System.out.println("Nao es dono de nenhum grupo");
		}else {
			for(String group : params[1].split(":"))
				System.out.println("- \t" + group);
		}
		System.out.println("----Grupos a que pertences---");
		if(params[2].equals("EMPTY")) {
			System.out.println("Nao pertences a nenhum grupo");
		}else {
			for(String group : params[2].split(":"))
				System.out.println("- \t" + group);
		}
		System.out.println("-----------------------------");

	}

	private HashMap<String, EncryptedKey> createGroupKey(String[] members, String newUser, int index) throws MequieException{

		HashMap <String,EncryptedKey> m = new HashMap<>();
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");

			kg.init(256);
			SecretKey newKey = kg.generateKey();

			for(String u : members) {
				PublicKey ku = this.acquireKey(u);
				Cipher c = Cipher.getInstance("RSA");
				c.init(Cipher.WRAP_MODE, ku);
				m.put(u, new EncryptedKey(index, c.wrap(newKey)));
			}

			if(newUser != null) {
				PublicKey kuu = this.acquireKey(newUser);
				Cipher cc = Cipher.getInstance("RSA");
				cc.init(Cipher.WRAP_MODE, kuu);
				m.put(newUser, new EncryptedKey(index,cc.wrap(newKey)));
			}
			return m;
		} catch (NoSuchAlgorithmException | CertificateException | FileNotFoundException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e) {
			throw new MequieException(e);
		}
	}



	private void processMessages(LinkedList<Message> msgs, HashMap<Integer, EncryptedKey> groupKeys) throws MequieException {
		try {
			Map<Integer, Cipher> ciphers = processKeys(groupKeys);
			while(msgs.peek() != null) {
				Message m = msgs.remove();
				if(m.isText()) {
					try {
						System.out.println(m.displayMsg(new String(ciphers.get(m.getKeyIndex()).doFinal(m.getContent()))));
					} catch (IllegalBlockSizeException | BadPaddingException e) {
						System.out.println("Erro ao processar mensagem");
					}
				}else {
					try {
						File f = new File(this.path + "/" + ((PhotoMessage) m).getPhotoName());
						if(!f.exists()) 
							f.getParentFile().mkdirs();
						Files.write(f.toPath(), ciphers.get(m.getKeyIndex()).doFinal(m.getContent()));
						System.out.println(m.displayMsg(null));
					} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
						System.out.println("Erro ao processar imagem " + ((PhotoMessage) m).getPhotoName());
					}
				}
			}
		} catch (MequieException e) {
			throw new MequieException(e);
		}
	}

	private void processHistory(String groupID, LinkedList<Message> history, HashMap<Integer, EncryptedKey> groupKeys) throws MequieException {
		try {
			Map<Integer, Cipher> ciphers = processKeys(groupKeys);
			System.out.println("-----------------Historico do grupo " + groupID + "-------------------");
			while(history.peek() != null) {
				Message m = history.remove();
				try {
					System.out.println(m.displayMsg(new String(ciphers.get(m.getKeyIndex()).doFinal(m.getContent()))));
				} catch (IllegalBlockSizeException | BadPaddingException e) {
					System.out.println("Erro ao processar mensagem do historico");
				}
			}
		} catch (MequieException e) {
			throw new MequieException(e);
		}
	}

	private Map<Integer, Cipher> processKeys(HashMap<Integer, EncryptedKey> groupKeys) throws MequieException {
		try {
			Map<Integer, Cipher> ciphers = new HashMap<>();
			PrivateKey userKey = this.getUserPrivateKey();
			for(EncryptedKey k : groupKeys.values()) {
				Cipher c = Cipher.getInstance("RSA");
				c.init(Cipher.UNWRAP_MODE, userKey);
				SecretKey unwrappedKey = (SecretKey) c.unwrap(k.getEncryptedKey(), "AES", Cipher.SECRET_KEY);

				Cipher cc = Cipher.getInstance("AES");
				cc.init(Cipher.DECRYPT_MODE, unwrappedKey);
				ciphers.put(k.getIndex(), cc);
			}				
			return ciphers;
		} catch (MequieException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new MequieException(e);
		}
	}

	private PublicKey acquireKey(String user) throws CertificateException, FileNotFoundException {
		File f = new File("PubKeys/" +user + ".cert");
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		return cf.generateCertificate(new FileInputStream(f)).getPublicKey();
	}

	private PrivateKey getUserPrivateKey() throws MequieException {
		try {
			return (PrivateKey) this.kstore.getKey(this.kstore.aliases().nextElement(), this.ksPassword.toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			throw new MequieException(e);
		}
	}
}
