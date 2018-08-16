package de.maanex.magic.wands;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Wand {

	private WandType	type;
	private short		skin;
	private String		name;
	private String		owner;
	private WandValues	values;

	//

	private Wand() {
	}

	public static WandBuilder builder() {
		return new WandBuilder(new Wand());
	}

	void apply(WandBuilder builder) {
		this.type = builder.type;
		this.skin = builder.skin;
		this.name = builder.name;
		this.owner = builder.owner;
		this.values = builder.values;
	}

	//

	public ItemStack asItem() {
		ItemStack s = new ItemStack(Material.WOODEN_HOE);
		s = setSkin(s, skin);
		ItemMeta m = s.getItemMeta();

		List<String> lore = new ArrayList<>();
		lore.add("§0" + type.getLoreName() + ":" + owner);

		if (values != null) {
			lore.add("");
			values.addLore(lore);
		}

		m.setDisplayName(name);
		m.setLore(lore);

		s.setItemMeta(m);
		return s;
	}

	/*
	 * 
	 */

	private static ItemStack setSkin(ItemStack s, short skin) {
		s.setDurability(skin);
		ItemMeta meta = s.getItemMeta();
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
		s.setItemMeta(meta);
		return s;
	}
}
