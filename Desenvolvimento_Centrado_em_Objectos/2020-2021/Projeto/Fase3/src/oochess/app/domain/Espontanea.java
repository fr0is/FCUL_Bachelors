package oochess.app.domain;

import java.time.LocalDateTime;

public class Espontanea extends Partida{

	public Espontanea(String user, String userAdv, LocalDateTime datahora) {
		super(user, userAdv, datahora);
	}

	@Override
	public String getCodDesafio() {
		return null;
	}

}
