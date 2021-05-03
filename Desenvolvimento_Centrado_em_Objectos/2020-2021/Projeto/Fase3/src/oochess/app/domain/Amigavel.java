package oochess.app.domain;

import java.time.LocalDateTime;

public class Amigavel extends Partida{
	private String codDesafio;

	public Amigavel(String user, String userAdv, LocalDateTime datahora, String codDesafio) {
		super(user, userAdv, datahora);
		this.codDesafio = codDesafio;
	}
	
	@Override
	public String getCodDesafio() {
		return this.codDesafio;
	}

}
