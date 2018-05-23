package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class Waterball extends MagicSpell {

	public Waterball() {
		super(40, "Wasserball", "Wie ein gelöschter Feuerball!\n(Nur zu stark gelöscht!)", 1, 8, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.COMMON);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {

		takeMana(caster, mods);
	}

}
