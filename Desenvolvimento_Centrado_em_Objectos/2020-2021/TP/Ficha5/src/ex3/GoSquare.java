package ex3;

public class GoSquare extends Square{

	private final int amountOnGo = 200;
	
	public GoSquare(String name) {
		super(name);
	}

	@Override
	public void landedOn(Player p) {
		p.changeAmount(this.amountOnGo);
	}

}
