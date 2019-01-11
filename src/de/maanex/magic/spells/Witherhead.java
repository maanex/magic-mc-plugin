package de.maanex.magic.spells;


import de.maanex.magic.MagicPlayer;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;


public class Witherhead extends MagicSpell {

	public Witherhead() {
		super(66, "Withersch‰del", "Schieﬂt einen Withersch‰del", 1, 8, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		caster.getMCPlayer().launchProjectile(org.bukkit.entity.WitherSkull.class);
		takeMana(caster, val);
	}
}
