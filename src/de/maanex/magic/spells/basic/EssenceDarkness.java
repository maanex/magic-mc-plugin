package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class EssenceDarkness extends MagicSpell {
	public EssenceDarkness() {
		super(29, "Essenz der Dunkelheit", "Der Zugang zu unendlich groﬂer Macht!", 50, 100, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.FORBIDDEN, WandType.DARK);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().sendMessage("ß4Lol wie? xD");
	}

}
