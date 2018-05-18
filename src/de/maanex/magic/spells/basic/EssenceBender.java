package de.maanex.magic.spells.basic;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;

public class EssenceBender extends MagicSpell {

	public EssenceBender(int id, String name, String desc, int manacost, int cooldown, SpellType type, SpellCategory category, SpellRarity rarity) {
		super(id, name, desc, manacost, cooldown, type, category, rarity);
		// TODO Auto-generated constructor stub
	}

	public EssenceBender(int id, String name, String desc, int manacost, int cooldown, SpellType type, SpellCategory category, SpellRarity rarity, WandType reqWandType) {
		super(id, name, desc, manacost, cooldown, type, category, rarity, reqWandType);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		// TODO Auto-generated method stub

	}

}
