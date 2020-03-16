import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class cliente {
	public static void main(String[] args) throws IOException {
		Socket echoSocket = new Socket("localhost", 23455);
		ObjectInputStream in = new ObjectInputStream(echoSocket.getInputStream());
		ObjectOutputStream out = new ObjectOutputStream(echoSocket.getOutputStream());
		FileInputStream inF = new FileInputStream("doc1.txt").read(,1,1);
		File ficheiro = new File("home/ALUNOSFC/fc51050/doc1.txt");
		

		/** 1
		out.writeObject("2");
		out.writeObject("2");
		
		try {
			Boolean resposta = (Boolean)in.readObject();
			System.out.println(resposta);
		}catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		*/
		 	
		
		in.close();
		out.close();
		echoSocket.close();
	}
}
