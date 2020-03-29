import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class Mequie {

	public static void main(String[] args) {
		try {
			verifyArgs(args);

			System.out.println("Mequie a inicializar......");
			System.out.println();

			String[] serverAddress = args[0].split(":");
			String ipAddress = serverAddress[0];
			int port = Integer.parseInt(serverAddress[1]);
			String user = args[1];
			String password = null;

			Scanner reader = new Scanner (System.in);

			if(args.length < 3) {
				System.out.print("Introduza a sua password:  ");
				password = reader.nextLine();

				System.out.println();
			}else{
				password = args[2];
			}

			Socket echoSocket = null;
			ObjectInputStream in = null;
			ObjectOutputStream out = null;


			echoSocket = new Socket(ipAddress, port);
			in = new ObjectInputStream(echoSocket.getInputStream());
			out = new ObjectOutputStream(echoSocket.getOutputStream());

			System.out.println("A autenticar");
			System.out.println("Aguarde........\n");
			
			out.writeObject(user);
			out.writeObject(password);

			try {
				String answer = (String) in.readObject();

				switch(answer) {
				case "001":
					System.out.println("Autenticado com sucesso \nOla " + user + " :)");					
					break;
				case "002":
					echoSocket.close();
					reader.close();
					throw new InvalidLoginException("Password incorreta!");
				case "003":
					echoSocket.close();
					reader.close();
					throw new InvalidLoginException("Esta conta ja se encontra autenticada noutra maquina");
				default:
					echoSocket.close();
					reader.close();
					throw new IllegalArgumentException("Argumentos em falta! \n Comando: Mequie <serverAddress> <localUserID> [password] \n <> Argumentos obrigatorios \n [] Argumentos opcionais");
				}
			}catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

			MequieClientHandler handler = new MequieClientHandler(user, in, out);

			boolean stop = false;
			String command = null;
			while(!stop) {
				System.out.print(">>>");
				command = reader.nextLine();
				String[] arguments= command.split(" ", 3);
				System.out.println();

				switch(arguments[0]) {
				case "create":
					if(arguments.length < 2) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: create <groupID>");
					}else {
						handler.create(arguments[1]);
					}
					break;

				case "addu":
					if(arguments.length < 3) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: addu <userID> <groupID>");
					}else if(arguments[2].equals("Geral")){
						System.out.println("Nao e possivel interagir com o grupo Geral");
					}else {
						handler.addu(arguments[1], arguments[2]);
					}
					break;

				case "removeu":
					if(arguments.length < 3) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: removeu <userID> <groupID>");
					}else if(arguments[2].equals("Geral")){
						System.out.println("Nao e possivel interagir com o grupo Geral");
					}else {
						handler.removeu(arguments[1], arguments[2]);
					}
					break;

				case "ginfo":
					if(arguments.length < 2) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: ginfo <groupID>");
					}else if(arguments[1].equals("Geral")){
						System.out.println("Nao e possivel interagir com o grupo Geral");
					}else {
						handler.ginfo(arguments[1]);
					}
					break;

				case "uinfo":
					handler.uinfo();
					break;

				case "msg":
					if(arguments.length < 3) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: msg <groupID> <msg>");
					}else if(arguments[1].equals("Geral")){
						System.out.println("Nao e possivel interagir com o grupo Geral");
					}else {
						handler.msg(arguments[1], arguments[2]);
					}
					break;

				case "photo":
					if(arguments.length < 3) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: photo <groupID> <photo>");
					}else if(arguments[1].equals("Geral")){
						System.out.println("Nao e possivel interagir com o grupo Geral");
					}else {
						handler.photo(arguments[1], arguments[2]);
					}
					break;

				case "collect":
					if(arguments.length < 2) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: collect <groupID>");
					}else if(arguments[1].equals("Geral")){
						System.out.println("Nao e possivel interagir com o grupo Geral");
					}else {
						handler.collect(arguments[1]);
					}
					break;

				case "history":
					if(arguments.length < 2) {
						System.out.println("Argumentos em falta");
						System.out.println("Uso correto: history <groupID>");
					}else if(arguments[1].equals("Geral")){
						System.out.println("Nao e possivel interagir com o grupo Geral");
					}else {
						handler.history(arguments[1]);
					}
					break;

				case "commands":
					handler.displayCommands();
					break;

				case "logout":
					stop = handler.logout();
					break;

				default:
					System.out.println("Comando nao reconhecido");
				}
			}

			System.out.println("Mequie a terminar");

			reader.close();
			out.close();
			in.close();
			echoSocket.close();
		} catch (ConnectException e) {
			System.out.println("O servidor nao existe ou nao esta disponivel \n");
			System.out.println("Mequie a terminar");
		} catch (SocketException e) {
			System.out.println("A ligacao com o servidor foi perdida :( \n");
			System.out.println("Mequie a terminar");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		} catch (InvalidLoginException e) {
			System.out.println(e.getMessage());
			System.out.println("\nMequie a terminar");
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

	}


	private static void verifyArgs(String[] args) {
		if(args.length < 1) {
			throw new IllegalArgumentException("Argumentos em falta! \n Comando: Mequie <serverAddress> <localUserID> [password] \n <> Argumentos obrigatorios \n [] Argumentos opcionais");
		}else if(AddressIsInvalid(args[0])){
			throw new IllegalArgumentException("O endereco eh invalido, por favor insira um endereco valido");
		}

	}

	private static boolean AddressIsInvalid(String address) {
		if(address.indexOf(":") < 0) 
			return true;
		try {
			Integer.parseInt(address.split(":")[1]);
			return false;
		}catch(NumberFormatException e) {
			return true;
		}


	}
}
