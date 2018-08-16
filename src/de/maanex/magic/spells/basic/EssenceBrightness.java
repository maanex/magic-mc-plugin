package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class EssenceBrightness extends MagicSpell {

	public EssenceBrightness() {
		super(30, "Essenz des Lichts", "Alles Gute ist darin gebündelt!", 50, 100, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.GODLIKE, WandType.LIGHT);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		caster.getMCPlayer().sendMessage("§4Lol wie? xD");
	}

}
