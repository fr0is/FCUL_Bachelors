package oochess.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Partida {
	protected LocalDate data;
	protected LocalTime hora;
	protected String user1;
	protected String user2;
	protected String resultado;
	
	protected Partida(String user,String userAdv,LocalDateTime datahora) {
		this.user1 = user;
		this.user2 = userAdv;
		this.data = datahora.toLocalDate();
		this.hora = datahora.toLocalTime();
	}

	public LocalDate getData() {
		return data;
	}

	public LocalTime getHora() {
		return hora;
	}
	
	public LocalDateTime getDataHora() {
		return this.data.atTime(this.hora);
		
	}

	public String getUser1() {
		return user1;
	}

	public String getUser2() {
		return user2;
	}

	public String getResultado() {
		return resultado;
	}
	
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public abstract String getCodDesafio();
	
}
