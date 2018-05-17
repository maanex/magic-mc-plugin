package de.maanex.magic.spells.basic;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class Elementum extends MagicSpell {

	public Elementum() {
		super(23, "Elementum", "Der Grundbaustein der Magie!", 0, 0, SpellType.NOT_USEABLE, SpellCategory.UTILITY, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().sendMessage("§7Du bist noch nicht bereit dazu!");
	}

}
