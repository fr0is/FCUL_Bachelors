package pleilist.app.domain;

import java.time.Duration;

public class Item {
	
	private Video video;
	private Duration durTransmissao;
	private Duration horaInicio;
	
	public Item(Video video) {
		this.video = video;
		//durTransmissao so eh inicializada em setDurTransmissao()
	}
	
	public Duration getHoraInicio() {
		return this.horaInicio;
	}

	public Duration getDuracaoTrans() {
		return this.durTransmissao;
	}

	public Video getVideo() {
		return this.video;
	}

	public void setDurTransmissao(Duration dur) {
		this.durTransmissao = dur;
	}

	public void setHoraInicio(Duration horaInicio) {
		this.horaInicio = horaInicio;
	}
}
