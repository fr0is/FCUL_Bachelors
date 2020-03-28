import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */

public class MequieServer {

	private GroupCatalog gc;
	private UserCatalog uc;

	public static void main(String[] args) {
		System.out.println("MequieServer a inicializar......");
		if(args.length > 0) {
			MequieServer server = new MequieServer();
			server.startServer(Integer.parseInt(args[0])); 
		}else {
			System.out.println("Argumentos em falta! \n Comando: MequieServer <port>");
		}
	}

	public void startServer (int port){
		ServerSocket sSoc = null;

		try{
			sSoc = new ServerSocket(port);
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
		}catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}

	private void loadCatalogs() throws FileNotFoundException {
		this.uc = new UserCatalog();
		this.gc = new GroupCatalog(this.uc);
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
				String password = null;

				try {
					user = (String)in.readObject();
					password = (String)in.readObject();
				}catch (ClassNotFoundException e1) {
					out.writeObject("005");
				}

				if(user != null && password != null) {

					System.out.println(user + " conectou-se");

					if(authenticate(user,password)) {
						if(uc.login(user)) {
							out.writeObject("001");
							System.out.println(user + ": Autenticou-se");
							MequieServerHandler handler = new MequieServerHandler(user,in,out,gc,uc);
							handler.routine();
						}else {
							out.writeObject("003");
							System.out.println(user + ": Este utilizador ja se encontra autenticado");
						}
					}else {
						out.writeObject("002");
						System.out.println(user + ": password incorreta");
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

		private boolean authenticate(String user, String password) throws IOException {
			System.out.println("A efetuar autenticacao");
			System.out.println();
			File users = new File("users.txt");
			if(!users.exists())
				users.createNewFile();
			String userData = lookUpUser(users,user);
			if(userData != null) {
				System.out.println(user + ": Detetado");
				return login(userData,user,password);
			}else {
				System.out.println(user + ": Nao detetado");
				System.out.println(user + ": A criar novo utilizador");
				createNewUser(users,user,password);
				return true;
			}
		}

		private void createNewUser(File users, String user, String passwd) {
			Writer w = null;
			try {
				w = new BufferedWriter(new FileWriter(users, true));
				String newUser = user + ":" + passwd + " \n";
				w.append(newUser);
				w.close();
				if(!uc.exists(user)) {
					uc.create(user);
					gc.addUser(user, "Geral");
				}
				System.out.println(user + ": Criado");
			}catch(IOException e) {
				System.err.println(e.getMessage());
				System.exit(-1);
			}

		}

		private boolean login(String userData, String user, String passwd) {
			String[] nameNpass = userData.split(":");
			return(nameNpass[0].equals(user) && nameNpass[1].equals(passwd));
		}

		private String lookUpUser(File users, String user) {
			String userData = null;
			Scanner leitor = null;
			try {
				leitor = new Scanner(users);
				while(leitor.hasNextLine()) {
					userData = leitor.nextLine();
					String[] nameNpass = userData.split(":");
					if(nameNpass[0].equals(user)) {
						leitor.close();
						return userData.split(" ")[0];
					}
				}
				leitor.close();
			}catch(IOException e) {
				System.err.println(e.getMessage());
				System.exit(-1);
			}
			return null;
		}
	}
}
