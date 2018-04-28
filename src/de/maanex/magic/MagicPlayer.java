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

	private Player	player;
	private int		mana, manaCap, maxMana;

	// temp
	public int					selected_spell	= 0;
	public int					nat_mana_reg_c	= 0;
	public List<MagicEffect>	applied_effects	= new ArrayList<>();

	private MagicPlayer(Player player) {
		this.player = player;
		this.mana = DefaultValues.MANA;
		this.manaCap = DefaultValues.MANA_REGEN_CAP;
		this.maxMana = DefaultValues.MAX_MANA;
	}

	public Player getMCPlayer() {
		return this.player;
	}

	public int getMana() {
		return this.mana;
	}

	public int getManaCap() {
		return this.manaCap;
	}

	public int getMaxMana() {
		return this.maxMana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void setManaCap(int manaCap) {
		this.manaCap = manaCap;
	}

	public void setMaxMana(int maxMana) {
		this.maxMana = maxMana;
	}

	public void addMana(int mana) {
		addMana(mana, true);
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
		VisualUpdater.update(this);
	}

	public boolean canAffordMana(int mana) {
		return this.mana >= mana;
	}

	public void addManaCap(int manaCap) {
		this.manaCap += manaCap;
	}

	public void addMaxMana(int maxMana) {
		this.maxMana += maxMana;
	}

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
	}

	/*
	 * 
	 */

	private static HashMap<Player, MagicPlayer> players = new HashMap<>();

	public static MagicPlayer get(Player player) {
		if (players.containsKey(player)) return players.get(player);

		MagicPlayer p = new MagicPlayer(player);
		if (Database.getConfig().getStringList("players").contains(player.getUniqueId().toString())) {
			ManaDbInterface.parseValues(p);
		}
		players.put(player, p);
		return p;
	}

	public static void saveAllToDB() {
		List<String> uuids = new ArrayList<>();
		players.forEach((k, v) -> {
			ManaDbInterface.saveValues(v);
			uuids.add(k.getUniqueId().toString());
		});

		Database.getConfig().set("players", uuids);
	}

}
