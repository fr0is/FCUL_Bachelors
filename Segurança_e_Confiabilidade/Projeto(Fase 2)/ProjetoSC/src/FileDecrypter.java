import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */
public class FileDecrypter {

	private FileDecrypter() {}

	public static void encryptFile(String tempPath, File file, Key k) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException{
		File temp = new File(tempPath);
		FileInputStream fis;
		FileOutputStream fos;
		CipherOutputStream cos;
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.ENCRYPT_MODE, k);

		fis = new FileInputStream(temp);
		fos = new FileOutputStream(file);

		cos = new CipherOutputStream(fos, c);
		byte[] b = new byte[16];  
		int i = fis.read(b);
		while (i != -1) {
			cos.write(b, 0, i);
			i = fis.read(b);
		}

		cos.close();
		fis.close();
		fos.close();
		temp.delete();
	}

	public static String decryptFile(File file, Key k) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException{
		String path = "temp/" + generateRandomString();
		File temp = new File(path);
		temp.getParentFile().mkdirs();
		temp.createNewFile();
		FileInputStream fis;
		FileOutputStream fos;
		CipherOutputStream cos;
		Cipher c = Cipher.getInstance("AES");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(k.getEncoded(), "AES"));

		fis = new FileInputStream(file);
		fos = new FileOutputStream(temp);

		cos = new CipherOutputStream(fos, c);
		byte[] b = new byte[16];  
		int i = fis.read(b);
		while (i != -1) {
			cos.write(b, 0, i);
			i = fis.read(b);
		}

		cos.close();
		fis.close();
		fos.close();
		return path;
	}

	public static void deleteTemp(String temp) {
		if(temp != null) {
			File t = new File(temp);
			if(t.exists())
				t.delete();
		}
	}

	private static String generateRandomString() {
		Random r = new Random();
		int leftLim = 97;
		int rightLim = 122;
		int length = 10;
		StringBuilder s = new StringBuilder(length);

		for(int i = 0; i < length; i++) {
			int lim = leftLim + (int) (r.nextFloat() * (rightLim-leftLim+1));
			s.append((char)lim);
		}
		return s.toString();
	}	
}
