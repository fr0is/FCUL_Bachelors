package oochess.app.domain.factories;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import oochess.app.elostrategies.EloStrategy;

public class EloStrategyFactory {

	private Properties prop = new Properties();
	private static final String STRATEGY_KEY = "ELO_STRATEGY";
	private static final String CONFIGURATION_FILE = "preferences.properties";
	private static EloStrategyFactory INSTANCE;

	private EloStrategyFactory() {}

	public static EloStrategyFactory getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new EloStrategyFactory();
		}
		return INSTANCE;
	}

	public EloStrategy getEloStrategy() {
		try {
			FileInputStream fileStream = new FileInputStream(CONFIGURATION_FILE);
			prop.load(fileStream);
			String classeAUsar = prop.getProperty(STRATEGY_KEY);
			Class<?> classElo = Class.forName(classeAUsar);
			Constructor<?> constructor = classElo.getDeclaredConstructor();
			return (EloStrategy) constructor.newInstance();
		} catch (IOException
				| ClassNotFoundException 
				| NoSuchMethodException 
				| SecurityException 
				| InstantiationException 
				| IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} 
	}

}

