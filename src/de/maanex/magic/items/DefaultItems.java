package de.maanex.magic.items;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;


public class DefaultItems {

	public DefaultItems() {
	}

	public static final String	SPELLBOOK_NAME	= "§1Spellbook";
	public static final String	MANAPOT_NAME	= "§3Mana Trank";

	public static ItemStack getBasicWand(WandModifiers modifiers) {
		return WandBuilder.get(Environment.NORMAL).withMods(modifiers).build();
	}

	public static ItemMeta applyWandModifiersr(ItemMeta m, WandModifiers mod, WandType type) {
		List<String> lore = new ArrayList<>();

		lore.add("§0" + type.getLoreName());
		lore.add("§0");
		lore.add("§3Manaverbrauch:§b " + dyn(mod.getManause()) + "%");
		lore.add("§5Sicherheit:§d " + dyn(mod.getSavety()) + "%");
		lore.add("§1Energie:§9 " + dyn(mod.getEnergy()));

		m.setLore(lore);
		return m;
	}

	private static String dyn(int r) {
		if (r == -1) return "?";
		return r + "";
	}

	public static ItemStack getBasicSpell(MagicSpell spell) {
		ItemStack out = new ItemStack(Material.PAPER);
		ItemMeta m = out.getItemMeta();

		String namecc = "§3";
		if (spell.getRequiredWandType() != null) namecc = spell.getRequiredWandType().getDisplayname().substring(0, 2);
		m.setDisplayName(namecc + spell.getName());
		m.addEnchant(Enchantment.MENDING, 1, false);

		List<String> lore = new ArrayList<>();

		lore.add("§0" + spell.getID());
		lore.add("§d" + spell.getDesc());
		lore.add("§0");
		lore.add("§6" + spell.getManacost() + " Mana");

		m.setLore(lore);

		out.setItemMeta(m);
		return out;
	}

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
