package ex3;

import java.util.List;

public class Player {
	private String name;
	private Piece piece;
	private Board board;
	//private Die[] die;
	private List<Die> die;
	private int amount = 50;;

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
	
	public void changeAmount(int d) {
		this.amount += d;
	}

	public int getAmount() {
		return this.amount;
	}

	public void goToJail() {
		this.piece.setLocation(this.board.getJailSquare());
	}
}
