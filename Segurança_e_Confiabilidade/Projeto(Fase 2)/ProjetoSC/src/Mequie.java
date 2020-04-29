import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Scanner;

import javax.net.SocketFactory;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class Mequie {
	
	public static void main(String[] args) {
		try {
			System.out.println("Mequie a inicializar");
			verifyArgs(args);
			System.out.println("Aguarde por favor.....");

			String[] serverAddress = args[0].split(":");
			String ipAddress = serverAddress[0];
			int port = Integer.parseInt(serverAddress[1]);
			String truststore = args[1];
			String keystore = args[2];
			String ksPassword = args[3];
			String user = args[4];

			Scanner reader = new Scanner (System.in);
			KeyStore kstore;

			FileInputStream kfile = new FileInputStream(keystore);
			kstore = KeyStore.getInstance("JCEKS");
			kstore.load(kfile, ksPassword.toCharArray()); 

			System.setProperty("javax.net.ssl.trustStore", truststore);
			System.setProperty("javax.net.ssl.trustStoreType", "JCEKS");
			SocketFactory sf = SSLSocketFactory.getDefault();
			SSLSocket echoSocket = (SSLSocket) sf.createSocket(ipAddress, port);
			ObjectInputStream in = null;
			ObjectOutputStream out = null;
			
			in = new ObjectInputStream(echoSocket.getInputStream());
			out = new ObjectOutputStream(echoSocket.getOutputStream());

			System.out.println("A autenticar");
			System.out.println("Aguarde........\n");

			out.writeObject(user);

			try {
				long nonce = (long) in.readObject();
				boolean unknown = (boolean) in.readObject();
				if(unknown) {
					byte[] cert = kstore.getCertificate(kstore.aliases().nextElement()).getEncoded();
					out.writeObject(user + ".cert");
					out.writeObject(cert);
					Signature s = getSignature(nonce,user,kstore,ksPassword);
					out.writeObject(nonce);
					out.writeObject(s.sign());
				}else{
					Signature s = getSignature(nonce,user,kstore,ksPassword);
					out.writeObject(s.sign());
				}

				switch((String)in.readObject()) {
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
				case "004":
					echoSocket.close();
					reader.close();
					throw new InvalidLoginException("Argumentos em falta! \\n Comando: Mequie <serverAddress> <truststore> <keystore> <keystore-password> <localUserID>");
				default:
					echoSocket.close();
					reader.close();
					throw new IllegalArgumentException("Ocorreu um erro no servidor");
				}
			}catch (ClassNotFoundException | UnrecoverableKeyException | InvalidKeyException | SignatureException e) {
				e.printStackTrace();
			} 

			MequieClientHandler handler = new MequieClientHandler(user, in, out, kstore, ksPassword);
			
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
		} catch (SSLException |SocketException e) {
			System.out.println("A ligacao com o servidor foi perdida :( \n");
			System.out.println("Mequie a terminar");
		} catch (IOException | InvalidLoginException | IllegalArgumentException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
			System.out.println(e.getMessage());
			System.out.println("\nMequie a terminar");
		}

	}


	private static Signature getSignature(long nonce, String user, KeyStore kstore, String ksPassword) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		PrivateKey pk = (PrivateKey) kstore.getKey(kstore.aliases().nextElement(), ksPassword.toCharArray());
		Signature s = Signature.getInstance("MD5withRSA");
		s.initSign(pk);
		byte buf[] = ByteBuffer.allocate(Long.BYTES).putLong(nonce).array();
		s.update(buf);
		return s;
	}


	private static void verifyArgs(String[] args) {
		if(args.length < 5) {
			throw new IllegalArgumentException("Argumentos em falta! \n Comando: Mequie <serverAddress> <truststore> <keystore> <keystore-password> <localUserID>");
		}else if(AddressIsInvalid(args[0])){
			throw new IllegalArgumentException("O endereco eh invalido, por favor insira um endereco valido");
		}
		File ks = new File(args[2]);
		if(!ks.exists())
			throw new IllegalArgumentException("A keystore nao existe");
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
