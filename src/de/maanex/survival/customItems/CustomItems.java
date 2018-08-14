package de.maanex.survival.customItems;


import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.main.Main;
import de.maanex.utils.NBTEditor;


public enum CustomItems {

	CRYSTALLIT_PICKAXE(), //
	END_ORB(), //

	;

	private ItemStack item;

	private CustomItems() {
	}

	public ItemStack getItem() {
		return item;
	}

	//

	static {
		ItemMeta m;

		CRYSTALLIT_PICKAXE.item = new ItemStack(Material.STONE_HOE, 1);
		m = CRYSTALLIT_PICKAXE.item.getItemMeta();
		m.setDisplayName(rainbowText("Crystallit Spitzhacke"));
		m.setLore(Arrays.asList("§0crystallitpickaxe", "", "§6Zerstört jeden Block und lässt ihn droppen, jedoch nur ein mal!"));
		CRYSTALLIT_PICKAXE.item.setItemMeta(m);
		setSkin(CRYSTALLIT_PICKAXE.item, (short) 5);

		END_ORB.item = NBTEditor.getHead("http://textures.minecraft.net/texture/6201ae1a8a04df52656f5e4813e1fbcf97877dbbfbc4268d04316d6f9f753");
		m = END_ORB.item.getItemMeta();
		m.setDisplayName("§5End Orb");
		m.setLore(Arrays.asList("§0endorb"));
		END_ORB.item.setItemMeta(m);
		setSkin(END_ORB.item, (short) 5);
	}

	public static void init() {
		Bukkit.getPluginManager().registerEvents(new ItemCrystallitPickaxe(), Main.instance);
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

	private static String rainbowText(String s) {
		String colors = "123456edcba9", out = "";
		int l = colors.length(), c = 0;
		for (String q : s.split(""))
			out += "§" + colors.split("")[c++ % l] + q;
		return out;
	}
}
