import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class MequieServerHandler {

	private static final String SEPARATOR = " ";

	private String user;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private GroupCatalog gc;
	private UserCatalog uc;

	public MequieServerHandler(String user, ObjectInputStream in, ObjectOutputStream out, GroupCatalog gc, UserCatalog uc){
		this.user = user;
		this.in = in;
		this.out = out;
		this.gc = gc;
		this.uc = uc;
	}

	@SuppressWarnings("unchecked")
	public void routine() {
		boolean connected = true;
		String[] arguments;
		int command;
		while(connected) {
			try {
				String incoming = (String) in.readObject();
				arguments = incoming.split(SEPARATOR);
				command = Integer.parseInt(arguments[0]);
				switch(command) {
				case 000:
					connected = false;
					break;
				case 100:
					/*
					 * codigos de resposta
					 * 101 = grupo criado com sucesso
					 * 102 = o grupo ja existe
					 * 103 = Falta de argumentos
					 * 104 = Erro ao criar o grupo
					 */
					if(arguments.length >= 2) {
						String answer;
						HashMap<String,EncryptedKey> groupKey = (HashMap<String, EncryptedKey>) in.readObject();
						try {
							answer = create(user, arguments[1], groupKey)?"101":"102";
						} catch (MequieException e) {
							answer = "104";
							System.out.println(user + ": Erro ao criar grupo: " + e.getMessage());
						}
						out.writeObject(answer);
					}else {
						System.out.println(user + ": Erro ao criar grupo: argumentos em falta");
						out.writeObject("103");
					}
					break;
				case 200:
					/*
					 * codigos de resposta
					 * 201 = o utilizador alvo pode ser adicionado ao grupo
					 * 202 = o grupo nao existe
					 * 203 = o utilizador nao eh dono do grupo
					 * 204 = o utilizador alvo nao existe
					 * 205 = o utilizador alvo ja pertence ao grupo
					 * 206 = erro ao adicionar utilizador
					 */
					if(arguments.length >= 3) {
						String check = adduCheck(user,arguments[1],arguments[2]);
						if(check.equals("201")) {
							out.writeObject(check);
							int keyIndex = this.gc.getCurrentKeyIndex(arguments[2]);
							String[] members = this.gc.getMembersList(arguments[2]);
							out.writeObject(keyIndex);
							out.writeObject(members);
							if((boolean) in.readObject()) {
								HashMap<String,EncryptedKey> groupKey = (HashMap<String, EncryptedKey>) in.readObject();
								boolean sucess = addu(user,arguments[1],arguments[2], groupKey);
								out.writeObject(sucess);
							}else {
								System.out.println(user + ": Houve um erro no cliente");
							}
						}else {
							out.writeObject(check);
						}
					}else {
						System.out.println(user + ": Erro ao adicionar utilizador");
						out.writeObject("206");
					}
					break;
				case 300:
					/*
					 * codigos de resposta
					 * 301 = o utilizador alvo pode ser removido do grupo
					 * 302 = o grupo nao existe
					 * 303 = o utilizador nao eh dono do grupo
					 * 304 = o utilizador nao se pode remover do seu proprio grupo
					 * 305 = o utilizador nao existe
					 * 306 = o utilizador alvo nao existe no grupo
					 * 307 = erro ao remover utilizador
					 */
					if(arguments.length >= 3) {
						String check = removeuCheck(user,arguments[1],arguments[2]);
						if(check.equals("301")) {
							out.writeObject(check);
							int keyIndex = this.gc.getCurrentKeyIndex(arguments[2]);
							String[] members = this.takeUserOut(arguments[1],this.gc.getMembersList(arguments[2]));
							out.writeObject(keyIndex);
							out.writeObject(members);							
							if((boolean) in.readObject()) {
								HashMap<String,EncryptedKey> groupKey = (HashMap<String, EncryptedKey>) in.readObject();
								boolean sucess = removeu(user,arguments[1],arguments[2], groupKey);
								out.writeObject(sucess);
							}else {
								System.out.println(user + ": Houve um erro no cliente");
							}
						}else {
							out.writeObject(check);
						}
					}else {
						System.out.println(user + ": Erro ao remover utilizador");
						out.writeObject("307");
					}
					break;
				case 400:	
					/*
					 * codigos de resposta
					 * 401 = informacao do grupo enviada
					 * 402 = o grupo nao existe
					 * 403 = erro ao obter informacao do grupo
					 */
					if(arguments.length >= 2) {
						String[] info = ginfo(user,arguments[1]).split(" ",2);
						if(info[0].equals("401")) {
							out.writeObject(info[0]);
							out.writeObject(info[1]);
							System.out.println(user + ": A informacao do grupo " + arguments[1] + " foi enviada");
						}else {
							out.writeObject("402");
							System.out.println(user + ": O grupo " + arguments[1] + " nao existe");
						}
					}else {
						out.writeObject("403");
						System.out.println(user + ": Erro ao obter informacao do grupo");
					}
					break;
				case 500:
					/*
					 * codigo de resposta
					 * 501 = a informacao do utilizador foi enviada
					 */
					String[] data = uinfo(user).split(" ",2);
					out.writeObject(data[0]);
					out.writeObject(data[1]);
					System.out.println(user + ":foi enviada a sua informacao");
					break;
				case 600:
					/*
					 * codigos de resposta
					 * 601 = mensagem pode ser enviada para o grupo
					 * 602 = o grupo nao existe
					 * 603 = o utilizador nao esta nesse grupo
					 * 604 = Erro ao enviar a mensagem para o grupo
					 */
					if(arguments.length > 1) {
						String check = msgCheck(user,arguments[1]);
						if(check.equals("601")) {
							out.writeObject(check);
							try {
								EncryptedKey key = this.gc.getCurrentKey(arguments[1], user);
								out.writeObject(true);
								out.writeObject(key);
								if((boolean) in.readObject()) {
									TextMessage msg = (TextMessage) in.readObject();
									msg.markDateNTime();
									this.msg(user,arguments[1],msg);
								}else {
									System.out.println(user + ": Erro ao encriptar mensagem no lado do cliente");
								}
							} catch (MequieException e) {
								out.writeObject(false);
								System.out.println(user + ": Erro ao adquirir a chave mais recente do grupo " + arguments[1]);
							}
						}else {
							out.writeObject(check);
						}
					}else {
						out.writeObject("604");
						System.out.println(user + ": Erro ao adicionar msg ao grupo");
					}
					break;
				case 700:
					/*
					 * codigos de resposta
					 * 701 = Fotografia pode ser enviada para o grupo
					 * 702 = o grupo nao existe
					 * 703 = o utilizador nao esta nesse grupo
					 * 704 = Erro ao enviar a fotografia para o grupo
					 */
					if(arguments.length > 1) {
						String check = photoCheck(user,arguments[1]);
						if(check.equals("701")) {
							out.writeObject(check);
							try {
								EncryptedKey key = this.gc.getCurrentKey(arguments[1], user);
								out.writeObject(true);
								out.writeObject(key);
								if((boolean) in.readObject()) {
									PhotoMessage photo = (PhotoMessage) in.readObject();
									photo.markDateNTime();
									this.photo(user,arguments[1],photo);
								}else {
									System.out.println(user + ": Erro ao encriptar foto no lado do cliente");
								}
							} catch (MequieException e) {
								out.writeObject(false);
								System.out.println(user + ": Erro ao adquirir a chave mais recente do grupo " + arguments[1]);
							}
						}else {
							out.writeObject(check);
						}
					}else {
						out.writeObject("704");
						System.out.println(user + ": Erro ao adicionar photo ao grupo");
					}
					break;
				case 800:
					/*
					 * codigos de resposta
					 * 801 = todas as mensagens podem ser enviadas ao utilizador
					 * 802 = nao ha nada para enviar
					 * 803 = o grupo nao existe
					 * 804 = o utilizador nao pertence ao grupo
					 * 805 = Ocorreu um erro
					 */
					if(arguments.length >= 2) {
						String check = collectCheck(user, arguments[1]);
						if(check.equals("801")) {
							out.writeObject(check);
							try {
								HashMap<Integer,EncryptedKey> groupKeys = this.gc.getGroupKeys(arguments[1], user);
								LinkedList<Message> msgs = this.gc.collect(user, arguments[1], groupKeys.keySet());
								out.writeObject(true);
								if(msgs != null) {
									out.writeObject(true);
									out.writeObject(groupKeys);
									out.writeObject(msgs);
									System.out.println(user + ": todas as mensagens nao visualizadas do grupo " + arguments[1] + " foram enviadas");
								}else {
									out.writeObject(false);
									System.out.println(user + ": Ja viu todas as suas mensagens nao vistas do grupo " + arguments[1]);
								}
							} catch (MequieException e) {
								out.writeObject(false);
								System.out.println(user + ": Erro ao adquirir as chaves ou mensagens do grupo " + arguments[1]);
							}
						}else {
							out.writeObject(check);
						}
					}else {
						System.out.println(user + ": Erro ao obter as mensagens nao visualizadas deste grupo");
						out.writeObject("805");
					}
					break;
				case 900:
					/*
					 * codigos de resposta
					 * 901 = o historico pode ser enviado ao utilizador
					 * 902 = o grupo nao existe
					 * 903 = o utilizador nao pertence ao grupo
					 * 904 = Este grupo ainda nao tem historico
					 * 905 = Ocorreu um erro
					 */
					if(arguments.length >= 2) {
						String check = historyCheck(user, arguments[1]);
						if(check.equals("901")) {
							out.writeObject(check);
							try {
								HashMap<Integer,EncryptedKey> groupKeys = this.gc.getGroupKeys(arguments[1], user);
								LinkedList<Message> history = this.gc.history(arguments[1],groupKeys.keySet());
								out.writeObject(true);
								if(history != null) {
									out.writeObject(true);
									out.writeObject(groupKeys);
									out.writeObject(history);
									System.out.println(user + ": o historico do grupo " + arguments[1] + " foi enviado");
								}else {
									out.writeObject(false);
									System.out.println(user + ": nao tem historico por ver do grupo " + arguments[1]);
								}
							} catch (MequieException e) {
								out.writeObject(false);
								System.out.println(user + ": Erro ao adquirir as chaves ou historico do grupo " + arguments[1]);
							}
						}else {
							out.writeObject(check);
						}
					}else {
						System.out.println(user + ": Erro ao enviar o historico do grupo " + arguments[1]);
						out.writeObject("905");
					}
					break;
				default:
					System.out.println("Comando desconhecido");
				}
			} catch (IOException e) {
				System.out.println(user + ": Ocorreu um erro, este utilizador pode ter ido abaixo");
				connected = false;
				continue;
			} catch (ClassNotFoundException e) {
				System.out.println(user + ": Ocorreu um erro ao comunicar com o cliente");
				continue;
			}
		}
	}

	private boolean create(String user, String groupID, Map<String, EncryptedKey> groupKey) throws MequieException{
		if(!gc.exist(groupID)) {
			gc.create(user,groupID,groupKey);
			System.out.println(user + ": Criou o grupo " + groupID);
			return true;
		}else {
			System.out.println(user + ": O grupo " + groupID + " ja existe");
			return false;
		}
	}

	private boolean addu(String user, String targetUser, String groupID, Map<String, EncryptedKey> groupKey) {

		try {
			this.gc.addUser(targetUser,groupID, groupKey);
			System.out.println(user + ": Adicionou " + targetUser + " ao grupo " +  groupID);
			return true;
		} catch (MequieException e) {
			System.out.println(user + ": Erro ao adicionar " + targetUser + " ao grupo " +  groupID);
			return false;
		}
	}

	private String adduCheck(String user, String targetUser, String groupID) {
		if(!gc.exist(groupID)) {
			System.out.println(user + ": O grupo " + groupID + " nao existe");
			return "202";
		}
		if(!gc.owns(user,groupID)) {
			System.out.println(user + ": Nao eh dono do grupo " + groupID);
			return "203";
		}
		if(!uc.exists(targetUser)) {
			System.out.println(user + ": " + targetUser + " nao existe");
			return "204";
		}
		if(gc.isUserIn(targetUser, groupID)) {
			System.out.println(user + ": " + targetUser + " ja pertence ao grupo " + groupID);
			return "205";
		}
		return "201";
	}

	private boolean removeu(String user, String targetUser, String groupID, Map<String, EncryptedKey> groupKey) {
		try {
			this.gc.removeUser(targetUser,groupID, groupKey);
			System.out.println(user + ": Removeu " + targetUser + " do grupo " +  groupID);
			return true;
		} catch (MequieException e) {
			System.out.println(user + ": Erro ao remover " + targetUser + " do grupo " +  groupID);
			return false;
		}
	}

	private String removeuCheck(String user, String targetUser, String groupID) {
		if(!gc.exist(groupID)) {		
			System.out.println(user + ": O grupo " + groupID + " nao existe");
			return "302";
		}
		if(!gc.owns(user,groupID)) {
			System.out.println(user + ": Nao eh dono do grupo " + groupID);
			return "303";
		}
		if(user.equals(targetUser)) {
			System.out.println(user + ": tentou remover-se a si mesmo do grupo" + groupID);
			return "304";
		}
		if(!uc.exists(targetUser)) {
			System.out.println(user + ": " + targetUser + " nao existe");
			return "305";
		}
		if(!gc.isUserIn(targetUser, groupID)) {
			System.out.println(user + ": " + targetUser + " nao pertence ao grupo " + groupID);
			return "306";
		}
		return "301";
	}

	private String ginfo(String user, String groupID) {
		if(gc.exist(groupID)) {
			return "401 " + gc.info(user, groupID);
		}else {
			return "402";
		}
	}

	private String uinfo(String user) {
		return "501 " + this.uc.info(user);
	}

	private void msg (String user, String groupID, Message msg) {
		this.gc.sendMsg(msg, groupID);
		System.out.println(user + ": Enviou uma mensagem para o grupo " + groupID);
	}

	private String msgCheck (String user, String groupID) {
		if(!this.gc.exist(groupID)) {
			System.out.println(user + ": o grupo " + groupID + " nao existe");
			return "602";
		}else if(!this.gc.isUserIn(user, groupID)) {
			System.out.println(user + ": Nao pertence ao grupo " + groupID);
			return "603";
		}else {
			return "601";
		}
	}

	private void photo (String user, String groupID, Message photo) {
		this.gc.sendMsg(photo, groupID);
		System.out.println(user + ": Enviou uma foto para o grupo " + groupID);
	}

	private String photoCheck (String user, String groupID){
		if(!this.gc.exist(groupID)) {
			System.out.println(user + ": o grupo " + groupID + " nao existe");
			return "702";
		}else if(!this.gc.isUserIn(user, groupID)) {
			System.out.println(user + ": Nao pertence ao grupo " + groupID);
			return "703";
		}else {
			return "701";
		}
	}

	private String collectCheck(String user, String groupID) {
		if(!gc.exist(groupID)) {
			System.out.println(user + ": o grupo " + groupID + " nao existe");
			return "803";
		}else if(!gc.isUserIn(user, groupID)) {
			System.out.println(user + ": Nao pertence ao grupo " + groupID);
			return "804";
		}else {
			return "801";
		}
	}

	private String historyCheck (String user, String groupID) {
		if(!this.gc.exist(groupID)) {
			System.out.println(user + ": o grupo " + groupID + " nao existe");
			return "902";
		}else if(!this.gc.isUserIn(user, groupID)) {
			System.out.println(user + ": Nao pertence ao grupo " + groupID);
			return "903";
		}else {
			return "901";
		}
	}

	private String[] takeUserOut(String user, String[] membersList) {
		List<String> members = new ArrayList<>(Arrays.asList(membersList));
		members.remove(user);
		String[] userArray = new String[members.size()];
		return members.toArray(userArray);
	}
}
