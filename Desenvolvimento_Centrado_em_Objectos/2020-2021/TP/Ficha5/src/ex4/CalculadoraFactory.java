package ex4;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class CalculadoraFactory {

	private Properties prop = new Properties();

	// Receita para o padrão singleton
	// Passo #1: atributo static instance

	private static CalculadoraFactory INSTANCE;

	// Passo #2: construtor privado
	private CalculadoraFactory() {

	}

	// Passo #3: getInstance

	public static CalculadoraFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CalculadoraFactory();
		}
		return INSTANCE;
	}
	public ICalculadoraAdapter getCalculadora() throws IllegalArgumentException {
		try {

			FileInputStream fileStream = new FileInputStream("./calc.properties");
			prop.load(fileStream);
			String classeAUsar = prop.getProperty("classCalc");

			// Método 1 : if-then-elses
			//			if(classeAUsar.equals("Calculadora")) {
			//				return new Calculadora();
			//		    } else if(classeAUsar.equals("CalculadoraOpAdapter")) {
			//		    	return new CalculadoraOpAdapter();
			//		    } else if(classeAUsar.equals("CalculadoraPtAdapter")){
			//		    	return new CalculadoraPtAdapter();
			//		    } else return null;
			// Método 2 : classes e construtores (preferido)
			Class<?> classCalculadora = Class.forName(classeAUsar);
			Constructor<?> constructor = classCalculadora.getDeclaredConstructor();
			return (ICalculadoraAdapter) constructor.newInstance(2);
		} catch (IOException
				| ClassNotFoundException 
				| InstantiationException 
				| IllegalAccessException
				| SecurityException
				| IllegalArgumentException
				| NoSuchMethodException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
