package ex1;

import java.awt.Color;

public class Cube {
	private double face;
	private Color color;
	
	public Cube(double face,Color color) {
		this.face=face;
		this.color=color;
	}
	
	public double getFace() {
		return face;
	}

	public Color getColor() {
		return color;
	}
	
	public boolean fit(Cube e) {
		return this.face > e.face;
	}

}
