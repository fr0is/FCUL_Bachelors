package pleilist.app.domain;

import java.time.Duration;

public class Clip extends Video{

	private Duration duration;
	
	public Clip() {
		super();
	}
	
	/**
	 * Define a duracao deste clip
	 * @param duration - duracao do clip
	 * @requires duration != null
	 */
	public void defDuration(Duration duration) {
		this.duration = duration;
	}

	public Duration getDuracao() {
		return this.duration;
	}
}
