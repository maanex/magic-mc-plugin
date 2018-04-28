package de.maanex.magic.database;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

import de.maanex.magic.DefaultValues;


public class Database {

	private Database() {
	}

	private static HashMap<String, Object>	cache	= new HashMap<>();
	private static YamlConfiguration		config;

	static {
		config = YamlConfiguration.loadConfiguration(new File(DefaultValues.DATABASE_PATH));
	}

	//

	public static void save() {
		cache.forEach((k, v) -> config.set(k, v));

		try {
			config.save(new File(DefaultValues.DATABASE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//

	public static void set(String key, Object value) {
		cache.put(key, value);
	}

	public static Object get(String key) {
		if (cache.containsKey(key)) return cache.get(key);
		if (config.contains(key)) cache.put(key, config.get(key));
		return cache.get(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key, Class<T> type) {
		return (T) get(key);
	}

	public static YamlConfiguration getConfig() {
		return config;
	}

}
