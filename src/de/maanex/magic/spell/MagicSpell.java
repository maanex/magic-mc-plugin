package de.maanex.magic.spell;


import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.maanex.magic.MagicManager;
import de.maanex.magic.MagicPlayer;
import de.maanex.magic.VisualUpdater;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.magic.wands.WandValues.WandModifier;
import de.maanex.utils.ChatIcons;


public abstract class MagicSpell {

	private String			name, desc;
	private int				id, manacost, cooldown;
	private WandType		reqWandType	= null;
	private SpellType		type;
	private SpellCategory	category;
	private SpellRarity		rarity;
	private String[]		params;

	public MagicSpell(int id, String name, String desc, int manacost, int cooldown, SpellType type, SpellCategory category, SpellRarity rarity, String... params) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.manacost = manacost;
		this.cooldown = cooldown;
		this.type = type;
		this.category = category;
		this.rarity = rarity;
		this.params = params;
	}

	public MagicSpell(int id, String name, String desc, int manacost, int cooldown, SpellType type, SpellCategory category, SpellRarity rarity, WandType reqWandType, String... params) {
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.manacost = manacost;
		this.cooldown = cooldown;
		this.type = type;
		this.category = category;
		this.rarity = rarity;
		this.reqWandType = reqWandType;
		this.params = params;
	}

	protected abstract void onCastPerform(MagicPlayer caster, WandType type, WandValues val);

	protected boolean canAffortSpell(MagicPlayer caster, WandValues val) {
		return (int) (manacost * ((double) val.getMod(WandModifier.MANAUSE) / 100)) <= caster.getMana();
	}

	protected void takeMana(MagicPlayer caster, WandValues val) {
		caster.addMana((int) (-manacost * ((double) val.getMod(WandModifier.MANAUSE) / 100)));

		if (cooldown > 0 && !caster.getMCPlayer().getGameMode().equals(GameMode.CREATIVE)) caster.cooldown.put(this, cooldown);
		VisualUpdater.updateCooldown(caster, true);
		
		val.setXp(val.getXp() + manacost);
	}

	public void interaction(MagicPlayer player, WandType type, WandValues val) {
		if (canAffortSpell(player, val)) {
			if (player.cooldown.containsKey(this)) {
				player.getMCPlayer().sendMessage("§7Spell is on Cooldown!");
				return;
			}
			if (reqWandType != null) {
				if (type.equals(reqWandType)) onCastPerform(player, type, val);
				else player.getMCPlayer().sendMessage("§7Du benötigst für diesen Zauber einen geeigneten Zauberstab!");
			} else onCastPerform(player, type, val);
		} else player.getMCPlayer().sendMessage("§7Du hast nicht genug Mana!");
	}

	//

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

	public int getCooldown() {
		return cooldown;
	}

	public SpellType getType() {
		return type;
	}

	public SpellCategory getCategory() {
		return category;
	}

	public SpellRarity getRarity() {
		return rarity;
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

		List<String> lore = new ArrayList<>();

		lore.add("§0" + getID());
		if (getDesc().contains("\n")) for (String s : getDesc().split("\n"))
			lore.add("§7" + s);
		else lore.add("§7" + getDesc());
		lore.add("§0");

		if (params != null && params.length > 0) {
			for (String s : params)
				lore.add("§f" + ChatIcons.translate(s));
			lore.add("§0");
		}

		lore.add("§3✤ Mana: §b" + getManacost());
		lore.add("§2♼ Cooldown: §a" + getCooldown() + "s");
		lore.add("§0");
		lore.add("§9✷ §8Typ: §7" + getType().getDisplayName());
		lore.add("§c✵ §8Kategorie: §7" + getCategory().getDisplayName());
		lore.add("§d❊ §8Seltenheit: §7" + getRarity().getDisplayName());

		m.setLore(lore);

		out.setItemMeta(m);
		return out;
	}

	public void updateExistingItemStack(ItemStack stack) {
		stack.setType(Material.IRON_HOE);
		setSkin(stack, (short) id);

		ItemMeta m = stack.getItemMeta();

		String namecc = "§3";
		if (getRequiredWandType() != null) namecc = getRequiredWandType().getDisplayname().substring(0, 2);
		m.setDisplayName(namecc + getName());

		List<String> lore = new ArrayList<>();

		lore.add("§0" + getID());
		if (getDesc().contains("\n")) for (String s : getDesc().split("\n"))
			lore.add("§7" + s);
		else lore.add("§7" + getDesc());
		lore.add("§0");
		lore.add("§3✤ Mana: §b" + getManacost());
		lore.add("§2♼ Cooldown: §a" + getCooldown() + "s");
		lore.add("§0");
		lore.add("§9✷ §8Typ: §7" + getType().getDisplayName());
		lore.add("§c✵ §8Kategorie: §7" + getCategory().getDisplayName());
		lore.add("§d❊ §8Seltenheit: §7" + getRarity().getDisplayName());

		m.setLore(lore);

		stack.setItemMeta(m);
	}

	//

	public static MagicSpell parse(ItemStack s) {
		if (s == null) return null;
		if (!s.getType().equals(Material.IRON_HOE) || !s.hasItemMeta()) return null;

		ItemMeta me = s.getItemMeta();
		if (!me.hasLore()) return null;
		String l = me.getLore().get(0);
		if (!l.startsWith("§0")) return null;
		try {
			int i = Integer.parseInt(l.replace("§0", ""));
			for (MagicSpell p : MagicManager.getAllSpells())
				if (p.getID() == i) return p;
		} catch (Exception ex) {}
		return null;
	}

}
