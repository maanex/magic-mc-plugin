package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;


public class EssenceBender extends MagicSpell {

	public EssenceBender() {
		super(31, "Essenz der Elementarbendigung", "Der erste Schritt auf dem Weg zum Avatar!", 50, 100, SpellType.NOT_USEABLE, SpellCategory.BENDER, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		caster.getMCPlayer().sendMessage("§4Lol wie? xD");
	}

}
