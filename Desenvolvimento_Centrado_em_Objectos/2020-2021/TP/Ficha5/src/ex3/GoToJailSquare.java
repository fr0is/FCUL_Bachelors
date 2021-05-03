package ex3;

public class GoToJailSquare extends Square{

	public GoToJailSquare(String name) {
		super(name);
	}

	@Override
	public void landedOn(Player p) {
		p.goToJail();
	}

}
