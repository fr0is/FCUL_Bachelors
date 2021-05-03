package ex3;

public class IncomeTaxSquare extends Square{
	
	private final double taxa = 0.1;
	
	public IncomeTaxSquare(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void landedOn(Player p) {
		int amount = p.getAmount();
		int amountADeduzir = (int) (amount*this.taxa);
		p.changeAmount(-amountADeduzir);
	}

}
