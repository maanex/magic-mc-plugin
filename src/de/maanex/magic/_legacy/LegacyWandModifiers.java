package de.maanex.magic._legacy;


import java.util.List;

import org.bukkit.inventory.meta.ItemMeta;


public class LegacyWandModifiers {

	private int manause, savety, energy;

	public LegacyWandModifiers(int manause, int savety, int energy) {
		this.manause = manause;
		this.savety = savety;
		this.energy = energy;
	}

	public int getManause() {
		return manause;
	}

	public void setManause(int manause) {
		this.manause = manause;
	}

	public int getSavety() {
		return savety;
	}

	public void setSavety(int savety) {
		this.savety = savety;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public static LegacyWandModifiers fromItem(ItemMeta meta) {
		List<String> lore = meta.getLore();

		int manause = get(lore.get(2).split(" ")[1].replace("%", ""));
		int savety = get(lore.get(3).split(" ")[1].replace("%", ""));
		int energy = get(lore.get(4).split(" ")[1]);
		return new LegacyWandModifiers(manause, savety, energy);
	}

	private static int get(String s) {
		if (s.equals("?")) return -1;
		return Integer.parseInt(s);
	}

	public boolean isActivated() {
		return !(manause == -1 || savety == -1 || energy == -1);
	}

}
