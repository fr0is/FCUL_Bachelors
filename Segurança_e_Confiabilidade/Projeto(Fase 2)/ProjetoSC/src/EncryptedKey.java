import java.io.Serializable;

/*
 * Grupo 40 Seguranca e Confibilidade
 * 51023 - Alexandre Monteiro
 * 51050 - Antonio Frois
 * 15775 - Hugo Diogo
 */
public class EncryptedKey implements Serializable{
	
	private static final long serialVersionUID = 4527921191821299609L;

	private int index;
	private byte[] encKey;
	
	public EncryptedKey(int index, byte[] encKey) {
		this.index = index;
		this.encKey = encKey;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public byte[] getEncryptedKey() {
		return this.encKey;
	}

}
