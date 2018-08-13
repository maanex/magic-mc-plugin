package de.maanex.magic.manapots;


import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.customEffects.ManaRegenEffect;


public class ManapotMaker {

	private ManapotMaker() {
	}

	public static ManaRegenEffect getEffect(ItemStack s) {
		if (s == null || !s.hasItemMeta()) return null;
		ItemMeta m = s.getItemMeta();
		if (!m.hasLore()) return null;
		String one = m.getLore().get(0);
		if (!one.startsWith("§0manapot:")) return null;
		int mana = Integer.parseInt(one.split(":")[1]);
		int sek = Integer.parseInt(one.split(":")[2]);
		return new ManaRegenEffect(sek, mana);
	}

	public static ItemStack getStack(int mana, int sek) {
		ItemStack s = new ItemStack(Material.STONE_HOE);
		s = setSkin(s, (short) 4);
		ItemMeta m = s.getItemMeta();

		m.setLore(Arrays.asList(//
				"§0manapot:" + mana + ":" + sek, //
				"", //
				"§b" + mana + " §3Mana §7in", //
				"§e" + sek + " §6Sekunden"//
		));

		m.setDisplayName("§bManapot");

		s.setItemMeta(m);
		return s;
	}

	//

	private static ItemStack setSkin(ItemStack s, short skin) {
		s.setDurability(skin);
		ItemMeta meta = s.getItemMeta();
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		s.setItemMeta(meta);
		return s;
	}

}
