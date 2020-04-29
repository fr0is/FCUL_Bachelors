import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class MequieServer {

	private GroupCatalog gc;
	private UserCatalog uc;
	private File users;
	private KeyStore kstore;
	private String ksPassword;
	private SecretKey infoKey;

	public static void main(String[] args) {
		System.out.println("MequieServer a inicializar");
		if(args.length < 3) {
			System.out.println("Argumentos em falta! \n Comando: MequieServer <port> <keystore> <keystore-password>");
		}else {
			System.out.println("Aguarde por favor......");
			MequieServer server = new MequieServer();
			server.startServer(Integer.parseInt(args[0]),args[1],args[2]); 
		}
	}

	public void startServer (int port, String keystore, String password){
		SSLServerSocket sSoc = null;

		try{
			System.setProperty("javax.net.ssl.keyStore", keystore);
			System.setProperty("javax.net.ssl.keyStoreType", "JCEKS");
			System.setProperty("javax.net.ssl.keyStorePassword", password);
			ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
			sSoc = (SSLServerSocket) ssf.createServerSocket(port);

			FileInputStream kfile = new FileInputStream(keystore);
			this.kstore = KeyStore.getInstance("JCEKS");
			this.kstore.load(kfile, password.toCharArray()); 
			this.ksPassword = password;
			this.users = new File("serverData/users.txt");
			this.loadInfoKey();

			if(!this.users.exists()) {
				this.users.getParentFile().mkdirs();
				this.users.createNewFile();
			}

			loadCatalogs();
			System.out.println();
			System.out.println("Servidor ativo, a aguardar utilizadores");
			while(true) {
				try {
					Socket inSoc = sSoc.accept();
					ServerThread client = new ServerThread(inSoc);
					client.start();
				}
				catch (IOException e) {
					e.printStackTrace();
					sSoc.close();
				}
			}
		}catch (FileNotFoundException e) {
			System.out.println("Houve um erro ao carregar os dados dos catalogos");
			System.exit(-1);
		}catch(SocketException e) {
			System.out.println("Erro ao iniciar o servidor, verifique a password introduzida para aceder a keystore");
			System.exit(-1);
		}catch (IOException | ClassNotFoundException | KeyStoreException | InvalidKeyException | NoSuchAlgorithmException | CertificateException | NoSuchPaddingException | IllegalBlockSizeException e) {
			System.out.println("erro");
			e.printStackTrace();
			System.exit(-1);
		} catch(UnrecoverableKeyException e) {
			System.out.println("Erro ao carregar a chave de informacao do servidor");
			System.exit(-1);
		}
	}

	private void loadInfoKey() throws IOException, NoSuchAlgorithmException, KeyStoreException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, UnrecoverableKeyException, ClassNotFoundException {
		File key = new File("serverData/serverInfo.key");
		if(!key.exists()) {
			key.getParentFile().mkdirs();
			key.createNewFile();

			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(256);
			this.infoKey = kg.generateKey();

			PublicKey ku = kstore.getCertificate(kstore.aliases().nextElement()).getPublicKey();
			Cipher c = Cipher.getInstance("RSA");
			c.init(Cipher.WRAP_MODE, ku);
			byte[] wrappedKey = c.wrap(this.infoKey);

			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(key));
			oos.writeObject(wrappedKey);
			oos.close();
		}else {
			ObjectInputStream oiso = new ObjectInputStream(new FileInputStream("serverData/serverInfo.key"));
			PrivateKey kr = (PrivateKey) kstore.getKey(kstore.aliases().nextElement(), this.ksPassword.toCharArray());

			byte[] wrapped = (byte[]) oiso.readObject();
			oiso.close();

			Cipher ck = Cipher.getInstance("RSA");
			ck.init(Cipher.UNWRAP_MODE, kr);
			this.infoKey = (SecretKey) ck.unwrap(wrapped, "AES", Cipher.SECRET_KEY);
		}
	}

	private void loadCatalogs() throws FileNotFoundException{
		this.uc = new UserCatalog(this.infoKey);
		this.gc = new GroupCatalog(this.uc,this.infoKey);
	}

	class ServerThread extends Thread {

		private Socket socket = null;

		ServerThread(Socket userSoc) {
			socket = userSoc;
			System.out.println("Novo utilizador a ligar-se");
		}

		public void run(){
			try {
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

				String user = null;
				String answer = null;

				try {
					user = (String)in.readObject();
				}catch (ClassNotFoundException e1) {
					answer = "005";
				}

				if(user != null) {

					System.out.println(user + " conectou-se");
					String userData = lookUpUser(user);

					SecureRandom secureRandom = new SecureRandom();
					long nonce = secureRandom.nextLong();
					boolean unknown = userData == null;

					out.writeObject(nonce);
					out.writeObject(unknown);

					try {
						if(unknown) {
							String certFileName = (String) in.readObject();
							byte cert[] = (byte[]) in.readObject();

							long returnedNonce = (long) in.readObject();
							byte signature[] = (byte[]) in.readObject();
							if (nonce == returnedNonce && verifySignature(user,nonce,signature,cert)) {

								String newCertPath ="serverData/PubKeysStored/" + certFileName;
								File newCert = new File(newCertPath);
								if(!newCert.exists()) {
									newCert.getParentFile().mkdirs();
									newCert.createNewFile();
								}							
								Files.write(newCert.toPath(),cert);
								this.createNewUser(user, newCertPath);

								if(uc.login(user)) {
									answer = "001";
								}else {
									answer = "003";
								}
							}else {
								answer = "002";
							}
						}else {
							byte signature[] = (byte[]) in.readObject();
							String[] nameNpubKey = userData.split(":");
							File cert = new File(nameNpubKey[1]);
							if (verifySignature(user,nonce,signature,Files.readAllBytes(cert.toPath()))) {
								if(uc.login(user)) {
									answer = "001";
								}else {
									answer = "003";
								}
							}else {
								answer = "002";
							}
						}
					} catch (ClassNotFoundException | NoSuchAlgorithmException | InvalidKeyException | SignatureException | CertificateException e) {
						answer = "005";
					}

					switch(answer) {
					case "001":
						MequieServerHandler handler = new MequieServerHandler(user,in,out,gc,uc);
						out.writeObject("001");
						System.out.println(user + ": Autenticou-se com sucesso");
						handler.routine();
						break;
					case "002":
						out.writeObject("002");
						System.out.println(user + ": Autenticacao ou registo sem sucesso");
						break;
					case "003":
						out.writeObject("003");
						System.out.println(user + ": Este utilizador ja se encontra autenticado");
						break;
					default:
						out.writeObject("005");
						System.out.println(user + ": Ocorreu um erro");
					}
					uc.logout(user);
					System.out.println(user + ": desconectou-se");

				} else {
					System.out.println("username ou password em falta");
					out.writeObject("004");
				}

				out.close();
				in.close();
				socket.close();

			} catch (IOException e) {
				System.out.println("Erro ao ligar o utilizador");
			}
		}

		private void createNewUser(String user, String certFileName){
			String decryptedPath = null;
			try {
				decryptedPath = FileDecrypter.decryptFile(users, infoKey);
				Writer w = new BufferedWriter(new FileWriter(decryptedPath, true));
				String newUser = user + ":" + certFileName + " \n";
				w.append(newUser);
				w.close();
				FileDecrypter.encryptFile(decryptedPath, users, infoKey);
				if(!uc.exists(user)) {
					uc.create(user);
					try {
						gc.addUser(user, "Geral", null);
					} catch (MequieException e) {}
				}
				System.out.println(user + ": Registado no Sistema");
			}catch(IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				FileDecrypter.deleteTemp(decryptedPath);
				System.err.println(e.getMessage());
				System.exit(-1);
			}

		}

		private String lookUpUser(String user) {
			String userData = null;
			String decryptedPath = null;
			try {
				decryptedPath = FileDecrypter.decryptFile(users, infoKey);
				Scanner leitor = new Scanner(new File(decryptedPath));
				while(leitor.hasNextLine()) {
					userData = leitor.nextLine();
					String[] nameNpubKey = userData.split(":");
					if(nameNpubKey[0].equals(user)) {
						leitor.close();
						FileDecrypter.encryptFile(decryptedPath, users, infoKey);
						return userData.split(" ")[0];
					}
				}
				leitor.close();
				FileDecrypter.encryptFile(decryptedPath, users, infoKey);
			}catch(IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
				FileDecrypter.deleteTemp(decryptedPath);
				System.err.println(e.getMessage());
				System.exit(-1);
			}
			return null;
		}

		private boolean verifySignature(String user, long returnedNonce, byte[] signature, byte[] cert) throws CertificateException, FileNotFoundException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {			
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			Certificate c = cf.generateCertificate(new ByteArrayInputStream(cert));
			PublicKey pk = c.getPublicKey();
			Signature s = Signature.getInstance("MD5withRSA");
			s.initVerify(pk);
			s.update(ByteBuffer.allocate(Long.BYTES).putLong(returnedNonce).array());
			return s.verify(signature);
		}
	}
}
