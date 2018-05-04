package de.maanex.magic;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.wandsuse.WandType;


public abstract class MagicSpell {

	private String		name, desc;
	private int			id, manacost;
	private WandType	reqWandType	= null;

	public MagicSpell(int id, String name, String desc, int manacost) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.manacost = manacost;
	}

	public MagicSpell(int id, String name, String desc, int manacost, WandType reqWandType) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.manacost = manacost;
		this.reqWandType = reqWandType;
	}

	protected abstract void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods);

	protected boolean canAffortSpell(MagicPlayer caster, WandModifiers mods) {
		return (int) (manacost * ((double) mods.getManause() / 100)) <= caster.getMana();
	}

	protected void takeMana(MagicPlayer caster, WandModifiers mods) {
		caster.addMana((int) (-manacost * ((double) mods.getManause() / 100)));
	}

	public void interaction(MagicPlayer player, WandType type, WandModifiers mod) {
		if (canAffortSpell(player, mod)) {
			if (reqWandType != null) {
				if (type.equals(reqWandType)) onCastPerform(player, type, mod);
				else player.getMCPlayer().sendMessage("§7Du benötigst für diesen Zauber einen geeigneten Zauberstab!");
			} else onCastPerform(player, type, mod);
		} else player.getMCPlayer().sendMessage("§7Du hast nicht genug Mana!");
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}

	public int getID() {
		return this.id;
	}

	public int getManacost() {
		return this.manacost;
	}

	public WandType getRequiredWandType() {
		return this.reqWandType;
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

	public ItemStack getItemStack() {
		ItemStack out = new ItemStack(Material.IRON_HOE);

		setSkin(out, (short) id);

		ItemMeta m = out.getItemMeta();

		String namecc = "§3";
		if (getRequiredWandType() != null) namecc = getRequiredWandType().getDisplayname().substring(0, 2);
		m.setDisplayName(namecc + getName());
		m.addEnchant(Enchantment.MENDING, 1, false);

		List<String> lore = new ArrayList<>();

		lore.add("§0" + getID());
		lore.add("§d" + getDesc());
		lore.add("§0");
		lore.add("§6" + getManacost() + " Mana");

		m.setLore(lore);

		out.setItemMeta(m);
		return out;
	}

	public void updateExistingItemStack(ItemStack stack) {

		setSkin(stack, (short) id);

		ItemMeta m = stack.getItemMeta();

		String namecc = "§3";
		if (getRequiredWandType() != null) namecc = getRequiredWandType().getDisplayname().substring(0, 2);
		m.setDisplayName(namecc + getName());
		m.addEnchant(Enchantment.MENDING, 1, false);

		List<String> lore = new ArrayList<>();

		lore.add("§0" + getID());
		lore.add("§d" + getDesc());
		lore.add("§0");
		lore.add("§6" + getManacost() + " Mana");

		m.setLore(lore);

		stack.setItemMeta(m);
	}

}
