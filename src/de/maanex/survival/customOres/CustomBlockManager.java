package de.maanex.survival.customOres;


import org.bukkit.Bukkit;

import de.maanex.main.Main;


public class CustomBlockManager {

	private CustomBlockManager() {
	}

	public static void init() {
		Bukkit.getPluginManager().registerEvents(new NoLogStripping(), Main.instance);
		Bukkit.getPluginManager().registerEvents(new BlockPopulator(), Main.instance);
		Bukkit.getPluginManager().registerEvents(new BlocksDrop(), Main.instance);
		Bukkit.getPluginManager().registerEvents(new NoSpawneggUse(), Main.instance);
	}

}
