package pleilist.app.facade.dto;

import java.time.Duration;
import pleilist.app.domain.Video;

/**
 * Esta classe existe apenas para a camada dos handlers falar com os clientes. 
 * Nao eh a classe item do modelo de dominio!!!
 *
 */
public class Entrada {

	private Video video;
	private Duration durTransmissao;
	private Duration horaInicio;

	public Entrada(Video video, Duration durTransmissao, Duration horaInicio) {
		this.video = video;
		this.durTransmissao = durTransmissao;
		this.horaInicio = horaInicio;
	}
	
	public String getCodigoVideo() {
		return this.video.getCodigo();
	}
	
	public Duration getDurTransmissao() {
		return this.durTransmissao;
	}
	
	public Duration getHoraInicio() {
		return this.horaInicio;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Item: Item com o video ");
		sb.append(this.video.getCodigo() + "\nHora de Inicio: ");
		sb.append(this.horaInicio.toString());
		return sb.toString();
	}
}