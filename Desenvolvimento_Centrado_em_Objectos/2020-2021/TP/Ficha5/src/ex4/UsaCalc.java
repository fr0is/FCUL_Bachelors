package ex4;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class UsaCalc {
	public static void main(String[] args) throws IOException {
		Calculadora calc = new Calculadora(2);
		System.out.println(calc.add(3, 5));
		
		//Random entre 0(inclusive) e 3(exclusive)
		Random r = new Random();
		
		List<ICalculadoraAdapter> arrayCalcs = new ArrayList<ICalculadoraAdapter>();
		arrayCalcs.add(new Calculadora(2));
		arrayCalcs.add(new CalculadoraComplexaAdapter(2));
		arrayCalcs.add(new CaculadoraPTAdapter(2));
		
		ICalculadoraAdapter calculadora = arrayCalcs.get(r.nextInt(3));
		System.out.println(calculadora.add(1, 2));
		ICalculadoraAdapter calculadora1 = arrayCalcs.get(r.nextInt(3));
		System.out.println(calculadora1.add(1, 3));
		ICalculadoraAdapter calculadora2 = arrayCalcs.get(r.nextInt(3));
		System.out.println(calculadora2.add(2, 3));
		
		
	}
}

