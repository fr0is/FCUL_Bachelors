package ex4;

public class Calculadora implements ICalculadoraAdapter{
	
	public Calculadora(int a) {
		System.out.println("Calculadora");
	}

	public  int add (int v1, int v2) {
		return v1+v2;
	}
	
	public  int mult (int v1, int v2) {
		return v1*v2;
	}
}
