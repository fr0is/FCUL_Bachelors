package ex6;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Board implements Observer{
	private List<Square> squares;
	private static final int NUMBER_OF_SQUARES = 40;
	private static final int JAIL_INDEX = 20;
	private static final int START_INDEX = 20;

	public Board() {
		squares = new ArrayList<Square>(NUMBER_OF_SQUARES);
		String name;
		for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
			name = Integer.toString(i);
			if (i == START_INDEX) squares.add(new GoSquare(name));
			else if (i == JAIL_INDEX) squares.add(new RegularSquare(name));
			else squares.add(SquareFactory.makeRandomSquare(name));
		}
	}

	public Square getJailSquare() {
		return this.squares.get(JAIL_INDEX);
	}

	public Square getSquare(Square oldLoc, int somaFaces) {
		int oldLoca = this.squares.indexOf(oldLoc);
		int newLoc = (oldLoca+somaFaces) % NUMBER_OF_SQUARES;
		return this.squares.get(newLoc);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Piece && arg instanceof Square) {
			Piece p = (Piece) o;
			Square s = (Square) arg;
			System.out.println("Moveu-se de " + s.getName() + " para " + p.getLocation().getName());
		}
		
	}

	public Square getStartSquare() {
		return null;
	}

}
