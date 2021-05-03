package ex4;

public class CalculadoraComplexa {
	
	public  int operacao (int v1, int v2,Operacao op) {
		switch(op) {
			case SUM:
				return v1+v2;
			case SUB:
				return v1-v2;
			case MULT:
				return v1*v2;
			case DIV:
				return v1/v2;
			default:
				return 0;
		}
	}
	
}
