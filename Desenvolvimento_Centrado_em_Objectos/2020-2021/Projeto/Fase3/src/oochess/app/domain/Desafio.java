package oochess.app.domain;

import java.time.LocalDateTime;

public class Desafio {
	private LocalDateTime data;
	private String codigo;
	private String user1;
	private String userAdv;
	private String mensagem;
	private Torneio torneioAssociado;
	private double deltaELO;
	private Estado estado;
	
	public Desafio(LocalDateTime data1,String codigo1, String user1,String userAdv1, String msg1, Torneio t, double delta) {
		this.data = data1;
		this.codigo = codigo1;
		this.user1 = user1;
		this.userAdv = userAdv1;
		this.mensagem = msg1;
		this.torneioAssociado = t;
		this.deltaELO = delta;
		this.estado = Estado.PENDENTE;
	}
	
	public String getUser1() {
		return user1;
	}

	public double getDeltaELO() {
		return deltaELO;
	}

	public LocalDateTime getData() {
		return this.data;
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public String getUserAdv() {
		return this.userAdv;
	}
	
	public String getMensagem() {
		return this.mensagem;
	}
	
	public Torneio getTorneioAssociado() {
		return this.torneioAssociado;
	}
	
	public void setTorneioAssociado(Torneio nome) {
		this.torneioAssociado = nome;
	}
	
	public void setDeltaELO(double deltaElo) {
		this.deltaELO = deltaElo;
	}
	
	public void setAdversario(String uAdv) {
		this.userAdv = uAdv;
	}
	
	public void setDMH(LocalDateTime dataHora, String msg) {
		this.data = dataHora;
		this.mensagem = msg;
	}
	
	public void setCodigo(String nextCodigo) {
		this.codigo = nextCodigo;
	}

	public Estado getEstado() {
		return this.estado;
	}

	public void atualizaEstado(Estado e) {
		this.estado = e;
	}

	public Desafio atualizaDataHora(LocalDateTime datahora) {
		this.data = datahora;
		return this;
	}

	public boolean existeTorneioAssociado() {
		return this.torneioAssociado!=null;
	}
}
