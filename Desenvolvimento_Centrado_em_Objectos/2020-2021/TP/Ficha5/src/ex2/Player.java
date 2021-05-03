package ex2;

import java.util.List;

public class Player {
	private String name;
	private Piece piece;
	private Board board;
	//private Die[] die;
	private List<Die> die;

	public Player(String name,Board board,List<Die> die) {
		this.name = name;
		this.piece = new Piece();
		this.board = board;
		this.die = die;
	}

	public void takeTurn() {
		int fvTotal = 0;
		for(Die d: this.die){
			d.roll();
			fvTotal += d.getFaceValue();
		}
		Square oldLoc = this.piece.getLocation();
		Square newLoc = this.board.getSquare(oldLoc, fvTotal);
		this.piece.setLocation(newLoc);
	}
}
