package de.maanex.main;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import de.maanex.magic.MagicManager;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.ManaRegeneration;
import de.maanex.magic.SpellRecipe;
import de.maanex.magic.UseWand;
import de.maanex.magic.VisualUpdater;
import de.maanex.magic.crafting.HelpBook;
import de.maanex.magic.crafting.Spellbook;
import de.maanex.magic.crafting.Wands;
import de.maanex.magic.database.Database;
import de.maanex.magic.listener.JoinLeave;
import de.maanex.magic.listener.ManapotDrink;
import de.maanex.magic.listener.RunicTableUse;
import de.maanex.magic.missile.MagicMissile;
import de.maanex.magic.spells.AirBlast;
import de.maanex.magic.spells.ArrowStorm;
import de.maanex.magic.spells.Comet;
import de.maanex.magic.spells.Enderarm;
import de.maanex.magic.spells.Fireball;
import de.maanex.magic.spells.Firemine;
import de.maanex.magic.spells.Firepunch;
import de.maanex.magic.spells.Firering;
import de.maanex.magic.spells.Frostwave;
import de.maanex.magic.spells.HolyShield;
import de.maanex.magic.spells.Impetus;
import de.maanex.magic.spells.Knock;
import de.maanex.magic.spells.Levitate;
import de.maanex.magic.spells.Nitro;
import de.maanex.magic.spells.PainfullSting;
import de.maanex.magic.spells.Phase;
import de.maanex.magic.spells.ProtectionWall;
import de.maanex.magic.spells.Sniper;
import de.maanex.magic.spells.Strike;
import de.maanex.magic.spells.Stun;
import de.maanex.magic.spells.Taser;
import de.maanex.magic.spells.TheConnector;
import de.maanex.magic.spells.Timewarp;
import de.maanex.magic.spells.Warp;
import de.maanex.magic.spells.Waterball;
import de.maanex.magic.spells.Waterpunch;
import de.maanex.magic.spells.basic.AirSpirit;
import de.maanex.magic.spells.basic.EarthSpirit;
import de.maanex.magic.spells.basic.Elementum;
import de.maanex.magic.spells.basic.EssenceBender;
import de.maanex.magic.spells.basic.EssenceBrightness;
import de.maanex.magic.spells.basic.EssenceDarkness;
import de.maanex.magic.spells.basic.FireSpirit;
import de.maanex.magic.spells.basic.WaterSpirit;
import de.maanex.magic.spells.building.BlockSwarper;
import de.maanex.magic.spells.building.DeGrasser;
import de.maanex.magic.spells.building.TreeDemolisher;
import de.maanex.magic.spells.darkmagic.DarkSeal;
import de.maanex.magic.spells.darkmagic.MagmaWorm;
import de.maanex.magic.spells.darkmagic.TheSeeker;
import de.maanex.magic.spells.earthbender.EarthBenderBridge;
import de.maanex.magic.spells.earthbender.EarthBenderCannon;
import de.maanex.magic.spells.lightmagic.TrueSight;
import de.maanex.magic.spells.waterbender.WaterBenderSplash;
import de.maanex.news.News;
import de.maanex.survival.AntiExplode;
import de.maanex.survival.BeimSterben;
import de.maanex.survival.ForceResoucrepack;
import de.maanex.survival.Jetpack;
import de.maanex.survival.JoinNames;
import de.maanex.survival.Schlafenszeit;
import de.maanex.sysad.Backdoor;
import de.maanex.sysad.CpuTerminal;
import de.maanex.terrainGenerators.wastelands.generator.WastelandsGenerator;
import de.maanex.terrainGenerators.whitehell.WorldsAmbient;
import de.maanex.terrainGenerators.whitehell.generator.WhiteHellGenerator;
import de.maanex.test.ItemsWithTextures;
import de.maanex.test.MathEqu;


public class Main extends JavaPlugin {

	public static Main instance;

	public Main() {
		instance = this;
	}

	@Override
	public void onEnable() {
		registerSpells();
		registerSpellRecipes();

		Wands.registerRecipe();
		Spellbook.registerRecipe();
		HelpBook.registerRecipe();
		Jetpack.registerRecipe();

		News.init();
		getCommand("news").setExecutor(new News());
		getCommand("cpu").setExecutor(new CpuTerminal());
		getCommand("test").setExecutor(new ItemsWithTextures());
		getCommand("equ").setExecutor(new MathEqu());

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

		Bukkit.getPluginManager().registerEvents(MagicManager.getSpell(Phase.class), this);

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
			WorldsAmbient.tick();

			Levitate.tick();
			Enderarm.tick();
			Phase.tick();
		}, 1, 1);

		// Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
		// News.tick();
		// }, 5, 5);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			for (Player p : Bukkit.getOnlinePlayers())
				MagicPlayer.get(p).tick();
		}, 20, 20);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			VisualUpdater.updateAllFull();
		}, 40, 40);

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			ManaRegeneration.doTick();
		}, 20, 20);
	}

	private void registerSpells() {
		MagicManager.registerSpell(new Strike());
		MagicManager.registerSpell(new Comet());
		MagicManager.registerSpell(new ProtectionWall());
		MagicManager.registerSpell(new Knock());
		MagicManager.registerSpell(new AirBlast());
		MagicManager.registerSpell(new Nitro());
		MagicManager.registerSpell(new ArrowStorm());
		MagicManager.registerSpell(new Warp());
		MagicManager.registerSpell(new HolyShield());
		MagicManager.registerSpell(new Fireball());
		MagicManager.registerSpell(new TheSeeker());
		MagicManager.registerSpell(new TrueSight());
		MagicManager.registerSpell(new Frostwave());
		MagicManager.registerSpell(new PainfullSting());
		MagicManager.registerSpell(new MagmaWorm());
		MagicManager.registerSpell(new EarthBenderBridge());
		MagicManager.registerSpell(new EarthBenderCannon());
		MagicManager.registerSpell(new DarkSeal());
		MagicManager.registerSpell(new Levitate());
		MagicManager.registerSpell(new Stun());
		MagicManager.registerSpell(new Firepunch());
		MagicManager.registerSpell(new WaterBenderSplash());
		MagicManager.registerSpell(new Elementum());
		MagicManager.registerSpell(new FireSpirit());
		MagicManager.registerSpell(new EarthSpirit());
		MagicManager.registerSpell(new WaterSpirit());
		MagicManager.registerSpell(new AirSpirit());
		MagicManager.registerSpell(new Firemine());
		MagicManager.registerSpell(new EssenceDarkness());
		MagicManager.registerSpell(new EssenceBrightness());
		MagicManager.registerSpell(new EssenceBender());
		MagicManager.registerSpell(new TheConnector());
		MagicManager.registerSpell(new Impetus());
		MagicManager.registerSpell(new Sniper());
		MagicManager.registerSpell(new Taser());
		MagicManager.registerSpell(new Firering());
		MagicManager.registerSpell(new Timewarp());
		MagicManager.registerSpell(new Enderarm());
		MagicManager.registerSpell(new Waterpunch());
		MagicManager.registerSpell(new Waterball());
		MagicManager.registerSpell(new Phase());
		MagicManager.registerSpell(new DeGrasser());
		MagicManager.registerSpell(new TreeDemolisher());
		MagicManager.registerSpell(new BlockSwarper());
	}

	private void registerSpellRecipes() {
		// Base
		MagicManager.registerSpellRecipe(new SpellRecipe(Elementum.class, Elementum.class, FireSpirit.class, 48));
		MagicManager.registerSpellRecipe(new SpellRecipe(Elementum.class, Elementum.class, WaterSpirit.class, 48));
		MagicManager.registerSpellRecipe(new SpellRecipe(Elementum.class, Elementum.class, EarthSpirit.class, 48));
		MagicManager.registerSpellRecipe(new SpellRecipe(Elementum.class, Elementum.class, AirSpirit.class, 48));
		MagicManager.registerSpellRecipe(new SpellRecipe(Elementum.class, Elementum.class, EssenceDarkness.class, 1));
		MagicManager.registerSpellRecipe(new SpellRecipe(Elementum.class, Elementum.class, EssenceBrightness.class, 2));
		MagicManager.registerSpellRecipe(new SpellRecipe(Elementum.class, Elementum.class, EssenceBender.class, 5));

		// Fire Stuff
		MagicManager.registerSpellRecipe(new SpellRecipe(FireSpirit.class, FireSpirit.class, Fireball.class, 999));
		MagicManager.registerSpellRecipe(new SpellRecipe(FireSpirit.class, FireSpirit.class, Firepunch.class, 1));

		MagicManager.registerSpellRecipe(new SpellRecipe(Fireball.class, FireSpirit.class, Fireball.class, 80));
		MagicManager.registerSpellRecipe(new SpellRecipe(Fireball.class, FireSpirit.class, Comet.class, 19));

		MagicManager.registerSpellRecipe(new SpellRecipe(Fireball.class, Fireball.class, Fireball.class, 19));
		MagicManager.registerSpellRecipe(new SpellRecipe(Fireball.class, Fireball.class, Comet.class, 80));
		MagicManager.registerSpellRecipe(new SpellRecipe(Fireball.class, Fireball.class, Firepunch.class, 1));

		MagicManager.registerSpellRecipe(new SpellRecipe(Knock.class, FireSpirit.class, Firepunch.class, 10));
		MagicManager.registerSpellRecipe(new SpellRecipe(Knock.class, FireSpirit.class, FireSpirit.class, 45));
		MagicManager.registerSpellRecipe(new SpellRecipe(Knock.class, FireSpirit.class, Knock.class, 45));

		// Air Stuff
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, AirSpirit.class, AirBlast.class, 1));
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, AirSpirit.class, Levitate.class, 99));

		// Earth Stuff
		MagicManager.registerSpellRecipe(new SpellRecipe(EarthSpirit.class, EarthSpirit.class, HolyShield.class, 1));
		MagicManager.registerSpellRecipe(new SpellRecipe(EarthSpirit.class, EarthSpirit.class, ProtectionWall.class, 99));

		// Water Stuff
		// MagicManager.registerSpellRecipe(new SpellRecipe(WaterSpirit.class,
		// WaterSpirit.class, AirBlast.class, 1));
		// MagicManager.registerSpellRecipe(new SpellRecipe(WaterSpirit.class,
		// WaterSpirit.class, Levitate.class, 99));

		MagicManager.registerSpellRecipe(new SpellRecipe(Knock.class, WaterSpirit.class, Waterpunch.class, 10));
		MagicManager.registerSpellRecipe(new SpellRecipe(Knock.class, WaterSpirit.class, WaterSpirit.class, 45));
		MagicManager.registerSpellRecipe(new SpellRecipe(Knock.class, WaterSpirit.class, Knock.class, 45));

		// Bender Stuff
		MagicManager.registerSpellRecipe(new SpellRecipe(EssenceBender.class, EarthSpirit.class, EarthBenderCannon.class, 20));
		MagicManager.registerSpellRecipe(new SpellRecipe(EssenceBender.class, EarthSpirit.class, EarthBenderBridge.class, 20));

		MagicManager.registerSpellRecipe(new SpellRecipe(EssenceBender.class, WaterSpirit.class, WaterBenderSplash.class, 20));

		// Mixing Elements
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, WaterSpirit.class, Knock.class, 60));
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, WaterSpirit.class, Strike.class, 20));

		MagicManager.registerSpellRecipe(new SpellRecipe(EarthSpirit.class, WaterSpirit.class, Knock.class, 60));
		MagicManager.registerSpellRecipe(new SpellRecipe(EarthSpirit.class, WaterSpirit.class, Frostwave.class, 20));

		MagicManager.registerSpellRecipe(new SpellRecipe(EarthSpirit.class, FireSpirit.class, Knock.class, 60));
		MagicManager.registerSpellRecipe(new SpellRecipe(EarthSpirit.class, FireSpirit.class, Firemine.class, 20));

		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, FireSpirit.class, Knock.class, 60));
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, FireSpirit.class, Comet.class, 10));

		// Crossing Elements
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, EarthSpirit.class, Knock.class, 60));
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, EarthSpirit.class, ArrowStorm.class, 20));
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, EarthSpirit.class, Warp.class, 10));
		MagicManager.registerSpellRecipe(new SpellRecipe(AirSpirit.class, EarthSpirit.class, Nitro.class, 10));

		MagicManager.registerSpellRecipe(new SpellRecipe(WaterSpirit.class, FireSpirit.class, Knock.class, 60));
		MagicManager.registerSpellRecipe(new SpellRecipe(WaterSpirit.class, FireSpirit.class, PainfullSting.class, 20));
		MagicManager.registerSpellRecipe(new SpellRecipe(WaterSpirit.class, FireSpirit.class, Stun.class, 20));
	}

	//

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		switch (id) {
			case "0":
			case "wastelands":
				return new WastelandsGenerator();
			case "1":
			case "whitehell":
				return new WhiteHellGenerator();
			default:
				return null;
		}
	}

}
