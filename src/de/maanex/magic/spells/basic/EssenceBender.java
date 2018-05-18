package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class EssenceBender extends MagicSpell {

	public EssenceBender() {
		super(31, "Essenz der Elementarbendigung", "Der erste Schritt auf dem Weg zum Avatar!", 50, 100, SpellType.NOT_USEABLE, SpellCategory.BENDER, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().sendMessage("§4Lol wie? xD");
	}

}
