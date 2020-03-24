package pleilist.app.domain;

public class Utilizador {
	
	private String username;
	private String password;

	public Utilizador(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean autenticaCom(String usernameTest, String passwordTest) {
		return this.username.contentEquals(usernameTest) && 
				this.password.contentEquals(passwordTest);
	}

	public String getUsername() {//este metodo so foi usado para testar eu saber o nome do curador no clienteExemplo
		return this.username;
	}
}
