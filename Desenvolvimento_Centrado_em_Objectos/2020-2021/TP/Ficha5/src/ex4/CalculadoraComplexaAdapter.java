package ex4;

public class CalculadoraComplexaAdapter implements ICalculadoraAdapter{
	
	private CalculadoraComplexa calcComp = new CalculadoraComplexa();
	
	public CalculadoraComplexaAdapter(int a) {
		System.out.println("CalculadoraComp");
		calcComp = new CalculadoraComplexa();
	}
	
	@Override
	public int add(int v1, int v2) {
		return calcComp.operacao(v1, v2, Operacao.SUM);
	}

	@Override
	public int mult(int v1, int v2) {
		return calcComp.operacao(v1, v2, Operacao.MULT);
	}

}
