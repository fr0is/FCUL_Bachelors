package ex2;

import java.util.Random;

public class Die {
	private int faceValue;
	private Random rd = new Random();

	public int getFaceValue() {
		return this.faceValue;
	}

	public void roll() {
		this.faceValue = this.rd.nextInt(6)+1;
	}
}
