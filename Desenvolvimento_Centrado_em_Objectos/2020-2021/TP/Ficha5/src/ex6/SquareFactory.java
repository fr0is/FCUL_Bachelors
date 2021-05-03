package ex6;

import java.util.Random;

public class SquareFactory {

	private static Random r = new Random();
	
	public static Square makeRandomSquare(String name) {
		int i = r.nextInt(4);
		switch (i) {
			case 0:
				return new RegularSquare(name);
			case 1:
				return new GoSquare(name);
			case 2:
				return new IncomeTaxSquare(name);
			case 3:
				return new GoToJailSquare(name);
			default:
				return new RegularSquare(name);
		}
	}
}
