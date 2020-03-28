import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.Scanner;

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

	public MequieClientHandler(String user, ObjectInputStream in, ObjectOutputStream out) {
		this.in = in;
		this.out = out;
		this.path = "clientData/"+user+"_photoBox";
		File p = new File(this.path);
		if(!p.exists())
			p.mkdirs();
		displayCommands();
	}

	public void create(String groupID) throws SocketException {
		try {
			out.writeObject("100"+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "101":
				System.out.println("Criaste o grupo " + groupID);
				break;
			case "102":
				System.out.println("O grupo " + groupID + " ja existe");
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
		}
	}

	public void addu(String userID, String groupID) throws SocketException {
		try {
			out.writeObject("200"+SEPARATOR+userID+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "201":
				System.out.println("Adicionaste " + userID + " ao grupo " + groupID);
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
				System.out.println(userID + " removido do grupo " + groupID);
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
			String[] answer = incoming.split(" ");
			switch(answer[0]) {
			case "401":
				displayGinfo(groupID,answer);
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
			String[] answer = incoming.split(" ");
			displayUinfo(answer[1]);
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
			out.writeObject("600"+SEPARATOR+groupID+SEPARATOR+msg);
			String answer = (String) in.readObject();
			switch(answer) {
			case "601":
				System.out.println("Mensagem enviada para o grupo " + groupID);
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

	public void photo(String groupID, String photo) throws SocketException {
		File pic = new File(photo);
		if(pic.exists()) {
			try {
				out.writeObject("700"+SEPARATOR+groupID+SEPARATOR+pic.getName());
				
				byte[] content = Files.readAllBytes(pic.toPath());
				out.writeObject(content);
				
				String answer = (String) in.readObject();
				switch(answer) {
				case "701":
					System.out.println("Fotografia enviada para o grupo " + groupID);
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

	public void collect(String groupID) throws SocketException {
		try {
			out.writeObject("800"+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "801":
				System.out.println("Mensagens do grupo " + groupID);
				receiveCollected();
				System.out.println();
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

	private void receiveCollected() throws ClassNotFoundException, IOException {
		String info = (String) in.readObject();
		String[] numbers = info.split(" ");

		for(int i = 0; i < Integer.parseInt(numbers[0]); i++) {
			String msg = (String) in.readObject();
			System.out.println(msg);
		}
		if(Integer.parseInt(numbers[1]) != 0) {
			for(int j = 0; j < Integer.parseInt(numbers[1]); j++) {	
				String name = (String) in.readObject();
				File f = new File(this.path + "/" + name);
				if(!f.exists())
					f.getParentFile().mkdirs();
				byte[] content = (byte[]) in.readObject();
				Files.write(f.toPath(), content);
			}
		}
		out.writeObject("OK");
	}

	public void history(String groupID) throws SocketException {
		try {
			out.writeObject("900"+SEPARATOR+groupID);
			String answer = (String) in.readObject();
			switch(answer) {
			case "901":
				File h = new File("clientData/tempHistory");
				if(!h.exists())
					h.getParentFile().mkdirs();
				byte[] content = (byte[]) in.readObject();
				Files.write(h.toPath(), content);
				System.out.println("----------historico do grupo " + groupID + "----------");
				try {
					Scanner sc = new Scanner(h);
					while(sc.hasNextLine())
						System.out.println(sc.nextLine());
					sc.close();
					System.out.println();
				}catch (FileNotFoundException e) {
					System.out.println("Erro ao ler o historico deste grupo");
				}
				h.delete();
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
		System.out.println("Dono: " + answer[1]);
		System.out.println("Numero de membros neste grupo: " + answer[2]);
		if(answer.length > 3) {
			System.out.println("-----Membros neste grupo-----");
			for(int i = 3; i < answer.length; i++)
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

}
