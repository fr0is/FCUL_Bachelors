package ex4;

public class CaculadoraPTAdapter implements ICalculadoraAdapter{
	
	private CalculadoraPortuguesa calcPT = new CalculadoraPortuguesa();	
	
	public CaculadoraPTAdapter(int a) {
		System.out.println("CalculadoraPT");
		calcPT = new CalculadoraPortuguesa();
	}
	
	@Override
	public int add(int v1, int v2) {
		return calcPT.soma(v1,v2);
	}

	@Override
	public int mult(int v1, int v2) {
		return calcPT.produto(v1,v2);
	}
	
}
