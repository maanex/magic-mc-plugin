package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class Witherhead extends MagicSpell {

	public Witherhead() {
		super(66, "Withersch‰del", "Schieﬂt einen Withersch‰del", 1, 8, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		caster.getMCPlayer().launchProjectile(org.bukkit.entity.WitherSkull.class);
		takeMana(caster, mods);
	}
}
