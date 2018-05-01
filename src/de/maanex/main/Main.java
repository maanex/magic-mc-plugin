package de.maanex.main;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.ManaRegeneration;
import de.maanex.magic.VisualUpdater;
import de.maanex.magic.crafting.HelpBook;
import de.maanex.magic.crafting.Spellbook;
import de.maanex.magic.crafting.Wands;
import de.maanex.magic.database.Database;
import de.maanex.magic.listener.JoinLeave;
import de.maanex.magic.listener.ManapotDrink;
import de.maanex.magic.listener.RunicTableUse;
import de.maanex.magic.missile.MagicMissile;
import de.maanex.magic.spells.EarthBenderCannon;
import de.maanex.magic.spells.Levitate;
import de.maanex.magic.wandsuse.UseWand;
import de.maanex.news.News;
import de.maanex.survival.AntiExplode;
import de.maanex.survival.BeimSterben;
import de.maanex.survival.ForceResoucrepack;
import de.maanex.survival.Jetpack;
import de.maanex.survival.JoinNames;
import de.maanex.survival.Schlafenszeit;
import de.maanex.sysad.Backdoor;
import de.maanex.sysad.CpuTerminal;
import de.maanex.test.ItemsWithTextures;
import de.maanex.whitehell.WorldsAmbient;
import de.maanex.whitehell.generator.WhiteHellGenerator;


public class Main extends JavaPlugin {

	public static Main instance;

	public Main() {
		instance = this;
	}

	@Override
	public void onEnable() {
		Wands.registerRecipe();
		Spellbook.registerRecipe();
		HelpBook.registerRecipe();
		Jetpack.registerRecipe();

		News.init();
		getCommand("news").setExecutor(new News());
		getCommand("cpu").setExecutor(new CpuTerminal());
		getCommand("test").setExecutor(new ItemsWithTextures());

		registerListeners();
		startTimers();

		Bukkit.getOnlinePlayers().forEach(p -> Bukkit.getOnlinePlayers().forEach(r -> r.showPlayer(p)));
	}

	@Override
	public void onDisable() {
		MagicPlayer.saveAllToDB();
		Database.save();
		News.stop();

	}

	private void registerListeners() {
		// Magic
		Bukkit.getPluginManager().registerEvents(new JoinLeave(), this);
		Bukkit.getPluginManager().registerEvents(new UseWand(), this);
		Bukkit.getPluginManager().registerEvents(new RunicTableUse(), this);
		Bukkit.getPluginManager().registerEvents(new Spellbook(), this);
		Bukkit.getPluginManager().registerEvents(new ManapotDrink(), this);
		Bukkit.getPluginManager().registerEvents(new EarthBenderCannon(), this);

		// Survival
		// Bukkit.getPluginManager().registerEvents(new ServerlistPing(), this);
		Bukkit.getPluginManager().registerEvents(new AntiExplode(), this);
		Bukkit.getPluginManager().registerEvents(new BeimSterben(), this);
		Bukkit.getPluginManager().registerEvents(new Schlafenszeit(), this);
		Bukkit.getPluginManager().registerEvents(new Jetpack(), this);
		Bukkit.getPluginManager().registerEvents(new News(), this);
		Bukkit.getPluginManager().registerEvents(new ForceResoucrepack(), this);

		// Sysadmin
		Bukkit.getPluginManager().registerEvents(new Backdoor(), this);

		// System
		Bukkit.getPluginManager().registerEvents(new JoinNames(), this);

		// White Hell
		Bukkit.getPluginManager().registerEvents(new WorldsAmbient(), this);
	}

	private void startTimers() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			MagicMissile.doTick();
			Jetpack.tick();
			Levitate.tick();
			WorldsAmbient.tick();
		}, 1, 1);

		// Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
		// News.tick();
		// }, 5, 5);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			for (Player p : Bukkit.getOnlinePlayers())
				MagicPlayer.get(p).tick();
		}, 20, 20);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			VisualUpdater.updateAll();
		}, 40, 40);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			ManaRegeneration.doTick();
		}, 20, 20);
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new WhiteHellGenerator();
	}

}
