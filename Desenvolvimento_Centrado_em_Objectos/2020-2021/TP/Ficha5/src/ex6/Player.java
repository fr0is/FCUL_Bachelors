package ex6;

import java.util.List;

public class Player {
	private String name;
	private Piece piece;
	private Board board;
	//private Die[] die;
	private List<Die> die;
	private int amount = 50;
	private int money;
	private List<Die> dice;;

	@SuppressWarnings("deprecation")
	public Player(String name,Board board2, List<Die> dice2) {
		this.name = name;
		this.money = 0;
		this.board = board2;
		this.dice = dice2;
		this.piece = new Piece();
		this.piece.addObserver(this.board);
		this.piece.setLocation(this.board.getStartSquare());
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
