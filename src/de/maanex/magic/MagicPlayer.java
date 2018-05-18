package de.maanex.magic;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import de.maanex.magic.customEffects.MagicEffect;
import de.maanex.magic.customEffects.ManaLockEffect;
import de.maanex.magic.database.Database;
import de.maanex.magic.database.ManaDbInterface;


public class MagicPlayer {

	private Player				player;
	private int					mana, manaCap, maxMana;
	private List<SpellRecipe>	researchedRecipes;

	// temp
	public int							selected_spell	= 0;
	public int							nat_mana_reg_c	= 0;
	public List<MagicEffect>			applied_effects	= new ArrayList<>();
	public HashMap<MagicSpell, Integer>	cooldown		= new HashMap<>();

	private MagicPlayer(Player player) {
		this.player = player;
		this.mana = DefaultValues.MANA;
		this.manaCap = DefaultValues.MANA_REGEN_CAP;
		this.maxMana = DefaultValues.MAX_MANA;
		this.researchedRecipes = new ArrayList<>();
	}

	//

	public Player getMCPlayer() {
		return this.player;
	}

	//

	public int getMana() {
		return this.mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void addMana(int mana, boolean useCap) {
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			this.mana = this.maxMana;
			return;
		}

		if (hasEffect(ManaLockEffect.class)) {
			this.mana = 0;
			return;
		}

		if (useCap && mana > 0) {
			if (this.mana >= this.manaCap) return;
			if (this.mana + mana > this.manaCap) this.mana = this.manaCap;
			else this.mana += mana;
		} else this.mana += mana;
		this.mana = Math.max(0, this.mana);
		this.mana = Math.min(this.mana, this.maxMana);
		VisualUpdater.updateFull(this);
	}

	public void addMana(int mana) {
		addMana(mana, true);
	}

	public boolean canAffordMana(int mana) {
		return this.mana >= mana;
	}

	//

	public int getManaCap() {
		return this.manaCap;
	}

	public void setManaCap(int manaCap) {
		this.manaCap = manaCap;
	}

	public void addManaCap(int manaCap) {
		this.manaCap += manaCap;
	}

	//

	public int getMaxMana() {
		return this.maxMana;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public void addMaxMana(int maxMana) {
		this.maxMana += maxMana;
	}

	//

	public List<SpellRecipe> getResearchedRecipes() {
		return this.researchedRecipes;
	}

	public void setResearchedRecipes(List<SpellRecipe> newList) {
		this.researchedRecipes = newList;
	}

	public void researchRecipe(SpellRecipe res) {
		if (!researchedRecipes.contains(res)) researchedRecipes.add(res);
	}

	public void removeRecipe(SpellRecipe res) {
		researchedRecipes.remove(res);
	}

	public boolean hasResearchedRecipe(SpellRecipe res) {
		return researchedRecipes.contains(res);
	}

	//

	public void clearEffects() {
		applied_effects.clear();
	}

	public void applyEffect(MagicEffect e) {
		for (MagicEffect m : new ArrayList<>(applied_effects))
			if (m.getClass().equals(e.getClass())) applied_effects.remove(m);
		applied_effects.add(e);
	}

	public void removeEffect(Class<? extends MagicEffect> c) {
		for (MagicEffect m : new ArrayList<>(applied_effects))
			if (m.getClass().equals(c)) applied_effects.remove(m);
	}

	public boolean hasEffect(Class<? extends MagicEffect> c) {
		for (MagicEffect m : new ArrayList<>(applied_effects))
			if (m.getClass().equals(c)) return true;
		return false;
	}

	@SuppressWarnings("unchecked")
	public <T extends MagicEffect> T getEffect(Class<T> c) {
		for (MagicEffect m : new ArrayList<>(applied_effects))
			if (m.getClass().equals(c)) return (T) m;
		return null;
	}

	public void tick() {
		for (MagicEffect e : new ArrayList<>(applied_effects)) {
			e.duration--;
			if (e.duration <= 0) applied_effects.remove(e);
			e.tick(this);
		}

		List<MagicSpell> rem = new ArrayList<>();
		for (MagicSpell s : cooldown.keySet()) {
			int ticks = cooldown.get(s);

			ticks--;
			if (ticks > 0) cooldown.put(s, ticks);
			else rem.add(s);
		}
		rem.forEach(cooldown::remove);

		VisualUpdater.updateCooldown(this, false);
	}

	/*
	 * 
	 */

	private static HashMap<String, MagicPlayer> players = new HashMap<>();

	public static MagicPlayer get(Player player) {
		if (players.containsKey(player.getUniqueId().toString())) {
			if (players.get(player.getUniqueId().toString()).getMCPlayer() != player) players.get(player.getUniqueId().toString()).player = player;
			return players.get(player.getUniqueId().toString());
		}

		MagicPlayer p = new MagicPlayer(player);
		if (Database.getConfig().getStringList("players").contains(player.getUniqueId().toString())) {
			ManaDbInterface.parseValues(p);
		}
		players.put(player.getUniqueId().toString(), p);
		return p;
	}

	public static void saveAllToDB() {
		List<String> uuids = new ArrayList<>();
		players.forEach((k, v) -> {
			ManaDbInterface.saveValues(v);
			uuids.add(k);
		});

		Database.getConfig().set("players", uuids);
	}

}
