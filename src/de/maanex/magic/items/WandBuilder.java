package de.maanex.magic.items;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.WandType;


public class WandBuilder {

	private static HashMap<Short, String> skinNames = new HashMap<>();

	private WandType		type	= WandType.WOODEN;
	private WandModifiers	mods	= new WandModifiers(100, 100, 100);
	private Environment		environment;

	//

	static {
		skinNames.put((short) 1, "§bUnindentifizierter Zauberstab");
		skinNames.put((short) 2, "§bGewöhnlicher Zauberstab");
		skinNames.put((short) 3, "§bLangstab");
		skinNames.put((short) 4, "§bLichtbringer Stab");
		skinNames.put((short) 5, "§bVerstärkter Zauberstab");
		skinNames.put((short) 6, "§bSonnenstab");
		skinNames.put((short) 7, "§cDunkelstab");
		skinNames.put((short) 8, "§cStab der ewigen Nacht");
		skinNames.put((short) 9, "§6Lichtstab");
		skinNames.put((short) 10, "§6Stab des ewigen Tages");
		skinNames.put((short) 11, "§aNachthüter Stab");
		skinNames.put((short) 12, "§aNachtwächter Stab");
		skinNames.put((short) 13, "§aWeltenentdecker Stab");
		skinNames.put((short) 14, "§aWeltenreiser Stab");
	}

	private WandBuilder() {
	}

	//

	public static WandBuilder get(Environment worldEnvironment) {
		WandBuilder b = new WandBuilder();
		b.environment = worldEnvironment;
		return b;
	}

	public WandBuilder withType(WandType type) {
		this.type = type;
		return this;
	}

	public WandBuilder withMods(WandModifiers mods) {
		this.mods = mods;
		return this;
	}

	public ItemStack build() {
		ItemStack res = new ItemStack(Material.WOODEN_HOE, 1);
		short skin = getFittingSkin(mods, type, environment);
		setSkin(res, skin);
		ItemMeta m = res.getItemMeta();
		applyWandModifiers(m, mods, type);
		m.setDisplayName(skinNames.get(skin));
		res.setItemMeta(m);
		return res;
	}

	public void apply(ItemStack s) {
		s.setType(Material.WOODEN_HOE);
		short skin = getFittingSkin(mods, type, environment);
		setSkin(s, skin);
		ItemMeta m = s.getItemMeta();
		m.removeEnchant(Enchantment.MENDING);
		applyWandModifiers(m, mods, type);
		m.setDisplayName(skinNames.get(skin));
		s.setItemMeta(m);
	}

	/*
	 * 
	 */

	private static short getFittingSkin(WandModifiers mods, WandType type, Environment environment) {
		if (mods.getEnergy() == -1) return 1;

		if (type.equals(WandType.DARK)) {
			if (mods.getEnergy() >= 115) return 8;
			else return 7;
		}

		if (type.equals(WandType.LIGHT)) {
			if (mods.getEnergy() >= 115) return 10;
			else return 9;
		}

		if (environment.equals(Environment.NETHER)) {
			if (mods.getEnergy() > 110) return 12;
			if (mods.getManause() < 90) return 11;
		}

		if (environment.equals(Environment.THE_END)) {
			if (mods.getEnergy() > 110) return 14;
			if (mods.getManause() < 90) return 13;
		}

		if (mods.getEnergy() >= 115) return 6;
		if (mods.getEnergy() >= 105) return 5;
		if (mods.getManause() <= 85) return 4;
		if (mods.getManause() <= 95) return 3;

		return 2;
	}

	private static ItemStack setSkin(ItemStack s, short skin) {
		s.setDurability(skin);
		ItemMeta meta = s.getItemMeta();
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		s.setItemMeta(meta);
		return s;
	}

	private static ItemMeta applyWandModifiers(ItemMeta m, WandModifiers mod, WandType type) {
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
}
