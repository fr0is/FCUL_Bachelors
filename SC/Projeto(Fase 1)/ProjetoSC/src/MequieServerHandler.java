import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.Arrays;

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

	public MequieServerHandler(String user, ObjectInputStream in, ObjectOutputStream out, GroupCatalog gc, UserCatalog uc) {
		this.user = user;
		this.in = in;
		this.out = out;
		this.gc = gc;
		this.uc = uc;
	}

	public void routine() {
		boolean connected = true;
		String[] arguments;
		int command;
		while(connected) {
			try {
				String incoming = (String) in.readObject();
				arguments = incoming.split(SEPARATOR, 3);
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
					 */
					if(arguments.length >= 2) {
						String answer = create(user, arguments[1])?"101":"102";
						out.writeObject(answer);
					}else {
						System.out.println(user + ": Erro ao criar grupo: argumentos em falta");
						out.writeObject("103");
					}
					break;
				case 200:
					/*
					 * codigos de resposta
					 * 201 = o utilizador alvo adicionado ao grupo com sucesso
					 * 202 = o grupo nao existe
					 * 203 = o utilizador nao eh dono do grupo
					 * 204 = o utilizador alvo nao existe
					 * 205 = o utilizador alvo ja pertence ao grupo
					 * 206 = erro ao adicionar utilizador
					 */
					if(arguments.length >= 3) {
						String answer = addu(user,arguments[1],arguments[2]);
						out.writeObject(answer);
					}else {
						System.out.println(user + ": Erro ao adicionar utilizador");
						out.writeObject("206");
					}
					break;
				case 300:
					/*
					 * codigos de resposta
					 * 301 = o utilizador alvo foi removido do grupo com sucesso
					 * 302 = o grupo nao existe
					 * 303 = o utilizador nao eh dono do grupo
					 * 304 = o utilizador nao se pode remover do seu proprio grupo
					 * 305 = o utilizador nao existe
					 * 306 = o utilizador alvo nao existe no grupo
					 * 307 = erro ao remover utilizador
					 */
					if(arguments.length >= 3) {
						String answer = removeu(user,arguments[1],arguments[2]);
						out.writeObject(answer);
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
						String info = ginfo(user,arguments[1]);
						out.writeObject(info);
						if(info.split(" ",1)[0].equals("401")) {
							System.out.println(user + ": A informacao do grupo " + arguments[1] + " foi enviada");
						}else {
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
					String answer = uinfo(user);
					out.writeObject(answer);
					System.out.println(user + ":foi enviada a sua informacao");
					break;
				case 600:
					/*
					 * codigos de resposta
					 * 601 = mensagem enviada
					 * 602 = o grupo nao existe
					 * 603 = o utilizador nao esta nesse grupo
					 * 604 = Erro ao enviar a mensagem para o grupo
					 */
					if(arguments.length >= 3) {
						String status = msg(user,arguments[1],arguments[2]);
						out.writeObject(status);
					}else {
						out.writeObject("604");
						System.out.println(user + ": Erro ao adicionar msg ao grupo");
					}
					break;
				case 700:
					/*
					 * codigos de resposta
					 * 701 = Fotografia enviada
					 * 702 = o grupo nao existe
					 * 703 = o utilizador nao esta nesse grupo
					 * 704 = Erro ao enviar a fotografia para o grupo
					 */
					if(arguments.length >= 3) {
						try {
							String status = photo(user,arguments[1],arguments[2]);
							out.writeObject(status);
						} catch(IOException | ClassNotFoundException e) {
							out.writeObject("704");
							System.out.println(user + ": Erro ao adicionar photo ao grupo");
						}
					}else {
						out.writeObject("704");
						System.out.println(user + ": Erro ao adicionar photo ao grupo");
					}
					break;
				case 800:
					/*
					 * codigos de resposta
					 * 801 = todas as mensagens foram enviadas
					 * 802 = nao ha nada para enviar
					 * 803 = o grupo nao existe
					 * 804 = o utilizador nao pertence ao grupo
					 * 805 = Ocorreu um erro
					 */
					if(arguments.length >= 2) {
						if(!gc.exist(arguments[1])) {
							System.out.println(user + ": o grupo " + arguments[1] + " nao existe");
							out.writeObject("803");
						}else if(!gc.isUserIn(user, arguments[1])) {
							System.out.println(user + ": Nao pertence ao grupo " + arguments[1]);
							out.writeObject("804");
						}else {
							String[] collected = collect(user,arguments[1]);
							String status = sendCollected(collected);
							if(!status.equals("801")) {
								out.writeObject(status);
							}else {	
								gc.checkNupdateInbox(arguments[1]);
								System.out.println(user + ": Viu todas as suas mensagens nao visualizadas do grupo " + arguments[1]);
							}
						}
					}else {
						System.out.println(user + ": Erro ao obter as mensagens nao visualizadas deste grupo");
						out.writeObject("805");
					}
					break;
				case 900:
					/*
					 * codigos de resposta
					 * 901 = o historico foi enviado
					 * 902 = o grupo nao existe
					 * 903 = o utilizador nao pertence ao grupo
					 * 904 = Este grupo ainda nao tem historico
					 * 905 = Ocorreu um erro
					 */
					if(arguments.length >= 2) {
						String status = history(user,arguments[1]);
						if(!status.equals("901"))
							out.writeObject(status);
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

	private String sendCollected(String[] collected){
		int msg = Integer.parseInt(collected[0]);
		int pho = Integer.parseInt(collected[1]);
		if(msg + pho == 0) {
			System.out.println(user + ": Nao tem nenhuma mensagem por ver");
			return "802";
		}else {
			String[] messages = Arrays.copyOfRange(collected,2,2+msg);
			String[] photos = Arrays.copyOfRange(collected,2+msg,2+msg+pho);
			try {
				out.writeObject("801");
				out.writeObject(msg + " " + pho);
				if(msg != 0) {
					for(String m : messages) {
						out.writeObject(m);
					}
				}
				if(pho != 0) {
					for(String p : photos) {
						File photo = new File(p);
						byte[] content = Files.readAllBytes(photo.toPath());
						out.writeObject(photo.getName());
						out.writeObject(content);
					}
				}
				String ok;
				try {
					ok = (String) in.readObject();
				} catch (ClassNotFoundException e) {
					ok = "OK";
				}
				System.out.println(user + ": " + ok + " tudo enviado");
			} catch (IOException e) {
				System.out.println(user + ": Houve um erro ao enviar as mensagens");
				return "805";
			}
			return "801";
		}
	}

	private boolean create(String user, String groupID) {
		if(!gc.exist(groupID)) {
			gc.create(user,groupID);
			System.out.println(user + ": Criou o grupo " + groupID);
			return true;
		}else {
			System.out.println(user + ": O grupo " + groupID + " ja existe");
			return false;
		}
	}

	private String addu(String user, String targetUser, String groupID) {
		if(gc.exist(groupID)) {
			if(gc.owns(user,groupID)) {
				if(uc.exists(targetUser)) {
					if(gc.addUser(targetUser,groupID)) {
						System.out.println(user + ": Adicionou " + targetUser + " ao grupo " +  groupID);
						return "201";
					}else {
						System.out.println(user + ": " + targetUser + " ja pertence ao grupo " + groupID);
						return "205";
					}
				}else {
					System.out.println(user + ": " + targetUser + " nao existe");
					return "204";
				}
			}else {
				System.out.println(user + ": Nao eh dono do grupo " + groupID);
				return "203";
			}
		}else {
			System.out.println(user + ": O grupo " + groupID + " nao existe");
			return "202";
		}
	}

	private String removeu(String user, String targetUser, String groupID) {
		if(gc.exist(groupID)) {
			if(gc.owns(user,groupID)) {
				if(!user.equals(targetUser)) {
					if(uc.exists(targetUser)) {
						if(gc.removeUser(targetUser,groupID)) {
							System.out.println(user + ": Removeu " + targetUser + " do grupo " +  groupID);
							return "301";
						}else {
							System.out.println(user + ": " + targetUser + " nao pertence ao grupo " + groupID);
							return "306";
						}
					}else {
						System.out.println(user + ": " + targetUser + " nao existe");
						return "305";
					}
				}else {
					System.out.println(user + ": tentou remover-se a si mesmo do grupo" + groupID);
					return "304";
				}
			}else {
				System.out.println(user + ": Nao eh dono do grupo " + groupID);
				return "303";
			}
		}else {
			System.out.println(user + ": O grupo " + groupID + " nao existe");
			return "302";
		}
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

	private String msg (String user, String groupID, String msg) {
		if(!this.gc.exist(groupID)) {
			System.out.println(user + ": o grupo " + groupID + " nao existe");
			return "602";
		}else if(!this.gc.isUserIn(user, groupID)) {
			System.out.println(user + ": Nao pertence ao grupo " + groupID);
			return "603";
		}else {
			Message message = new TextMessage(user, msg);
			this.gc.sendMsg(message, groupID);
			System.out.println(user + ": Enviou uma mensagem para o grupo " + groupID);
			System.out.println("Mensagem: " + msg);
			return "601";
		}
	}

	private String photo (String user, String groupID, String photo) throws ClassNotFoundException, IOException {
		File f = new File("serverData/groupCatalogData/"+ groupID +"/photoBox/" + photo);
		if(!f.exists())
			f.getParentFile().mkdirs();
		byte[] content = (byte[]) in.readObject();
		if(!this.gc.exist(groupID)) {
			System.out.println(user + ": o grupo " + groupID + " nao existe");
			return "702";
		}else if(!this.gc.isUserIn(user, groupID)) {
			System.out.println(user + ": Nao pertence ao grupo " + groupID);
			return "703";
		}else {
			Files.write(f.toPath(), content);
			Message message = new PhotoMessage(user, photo);
			this.gc.sendMsg(message, groupID);
			System.out.println(user + ": Enviou uma foto para o grupo " + groupID);
			System.out.println("Foto: " + photo);
			return "701";
		}
	}

	private String[] collect (String user, String groupID) {
		String[] msgs = gc.collect(user,groupID);
		return msgs;
	}

	private String history (String user, String groupID) throws IOException {
		if(!this.gc.exist(groupID)) {
			System.out.println(user + ": o grupo " + groupID + " nao existe");
			return "902";
		}else if(!this.gc.isUserIn(user, groupID)) {
			System.out.println(user + ": Nao pertence ao grupo " + groupID);
			return "903";
		}else {
			File history = gc.history(groupID);
			if(history.exists()) {
				out.writeObject("901");
				byte[] content = Files.readAllBytes(history.toPath());
				out.writeObject(content);
				System.out.println(user + ": viu o historico do grupo " + groupID);
				return "901";
			}else {
				System.out.println(user + ": o grupo " + groupID + " ainda nao tem historico");
				return "904";
			}
		}
	}

}
