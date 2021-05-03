package ex3;
import java.util.ArrayList;
import java.util.List;

public class MGame {
	
	private static final int NUMBER_OF_DICE = 2;
	
	private int roundCount;
	
	private List<Die> dice = new ArrayList<Die>(NUMBER_OF_DICE);
	private List<Player> players = new ArrayList<Player>();
	private Board board = new Board();
	
	public MGame(List<String> playerNames) {
		for (int i = 0; i < NUMBER_OF_DICE; i++) {
			dice.add(new Die());
		}
		
		for (String name : playerNames) {
			players.add(new Player(name, board, dice));
		}
	}
	
	public void playGame(int nVezes) {
		for (int i = 0; i < nVezes; i++) {
			roundCount++;
			System.out.println(">> Ronda " + roundCount);
			playRound();
		}
	}
	
	private void playRound() {
		for (Player p: players) {
			System.out.println("É a vez do jogador: " + p);
			p.takeTurn();
		}
	}
}
