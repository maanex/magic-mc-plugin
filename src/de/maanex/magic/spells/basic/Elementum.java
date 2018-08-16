package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class Elementum extends MagicSpell {

	public Elementum() {
		super(23, "Elementum", "Der Grundbaustein der Magie!", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		caster.getMCPlayer().sendMessage("§7Du bist noch nicht bereit dazu!");
	}

}
