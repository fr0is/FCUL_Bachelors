package oochess.app.domain.factories;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import oochess.app.discordintegration.DiscordAdapter;

public class DiscordFactory {
	private static final String CONFIGURATION_FILE = "preferences.properties";
	private static final String DISCORD_KEY = "DISCORD_CLASS";
	private static final String DISCORD_TKN = "DISCORD_TOKEN";
	private Properties prop = new Properties();
	private static DiscordFactory INSTANCE;

	private DiscordFactory() {}
	
	public static DiscordFactory getInstance() {
		if (INSTANCE == null) { // Lazy Loading
			INSTANCE = new DiscordFactory();
		}
		return INSTANCE;
	}

	@SuppressWarnings("rawtypes")
	public DiscordAdapter getDiscordAdapter() {
		try {
			FileInputStream fileStream = new FileInputStream(CONFIGURATION_FILE);
			prop.load(fileStream);
			String classeAUsar = prop.getProperty(DISCORD_KEY);
			Class<?> classDiscord = Class.forName(classeAUsar);
			String token = prop.getProperty(DISCORD_TKN);
			Class[] carg = {String.class};
			Constructor<?> constructor = classDiscord.getDeclaredConstructor(carg);
			return (DiscordAdapter) constructor.newInstance(token);
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

