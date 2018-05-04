package de.maanex.magic.items;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class DefaultItems {

	public DefaultItems() {
	}

	public static final String	SPELLBOOK_NAME	= "§1Spellbook";
	public static final String	MANAPOT_NAME	= "§3Mana Trank";

	public static ItemStack getSpellbook() {
		ItemStack out = new ItemStack(Material.BOOK);
		ItemMeta m = out.getItemMeta();

		m.setDisplayName(SPELLBOOK_NAME);
		m.addEnchant(Enchantment.MENDING, 1, false);

		List<String> lore = new ArrayList<>();
		lore.add("§0");
		m.setLore(lore);

		out.setItemMeta(m);
		return out;
	}

	public static ItemStack getManaPot(int level) {
		ItemStack out = new ItemStack(Material.POTION);
		ItemMeta m = out.getItemMeta();

		m.setDisplayName(MANAPOT_NAME);
		m.addEnchant(Enchantment.MENDING, 1, false);

		List<String> lore = new ArrayList<>();
		lore.add("§0Manapot");
		lore.add("§1" + level);
		m.setLore(lore);

		out.setItemMeta(m);
		return out;
	}

}
