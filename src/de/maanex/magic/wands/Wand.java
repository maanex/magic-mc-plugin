package de.maanex.magic.wands;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Wand {

	public static final Wand OUTDATED = new Wand();

	//

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
		lore.add("§0" + type.getLoreName() + ":" + "na" /* + owner */); // TODO + owner ABER ES WIRD HALT ZU LANG SONST

		if (values != null) {
			lore.add("");
			values.addLore(lore);
		}

		m.setDisplayName(name);
		m.setLore(lore);

		s.setItemMeta(m);
		return s;
	}

	public static Wand fromItem(ItemStack s) {
		if (s == null) return null;
		if (!s.getType().equals(Material.WOODEN_HOE)) return null;
		if (!s.hasItemMeta()) return null;
		if (!s.getItemMeta().hasLore()) return null;

		ItemMeta m = s.getItemMeta();
		WandBuilder b = builder();

		if (!m.getLore().get(0).contains(":")) return OUTDATED; // OUTDATED WAND

		WandType type = WandType.getFromItem(s);
		if (type == null) return null;
		b.setType(type);
		b.setSkin(s.getDurability());

		if (type.equals(WandType.UNIDENTIFIED)) return b.build();

		b.setOwner(m.getLore().get(0).split(":")[1]);
		b.setValues(WandValues.fromLore(m.getLore()));

		return b.build();
	}

	//

	public WandType getType() {
		return type;
	}

	public short getSkin() {
		return skin;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}

	public WandValues getValues() {
		return values;
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
