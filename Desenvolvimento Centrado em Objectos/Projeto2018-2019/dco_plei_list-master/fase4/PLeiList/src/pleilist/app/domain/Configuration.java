package pleilist.app.domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configuration {

	private static final String CONFIGURATION_FILE = "config.properties";
	
	private static Configuration INSTANCE = null;

	public static Configuration getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new Configuration();
		}
		return INSTANCE;
	}

	private Properties prop = new Properties();

	private Configuration() {
		try {
			prop.load(new FileInputStream(CONFIGURATION_FILE));
		} catch (FileNotFoundException e) {
			System.out.println("Nao foi possivel encontrar o ficheiro "
		+ CONFIGURATION_FILE + ".");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getString(String chave) {
		return prop.getProperty(chave);
	}
	
	public List<String> getStringList(String chave) {
		String[] stratArray = getString(chave).split(";");
		List<String> stratList = new ArrayList<>();
		for(String strat: stratArray) {
			stratList.add(strat);
		}
		return stratList;
	}


	@SuppressWarnings("unchecked")
	public <T> T createInstance(String className, T defaultObject) {
		
		Class<T> c = null;
		try {
			c = (Class<T>) Class.forName(className);

		} catch (ClassNotFoundException e1) {
			System.out.println("Nao foi possivel encontrar a classe "
		+ className + ".");
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		T ins = defaultObject;
		try {
			Constructor<T> cons = c.getConstructor();
			ins = cons.newInstance();

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			//Nao faz nada o defaultObject será devolvido
		}
		return ins;
	}

	public <T> T createInstanceFromKey(String chave, T defaultObject) {
		String className = this.getString(chave);
		return this.createInstance(className, defaultObject);
	}
}