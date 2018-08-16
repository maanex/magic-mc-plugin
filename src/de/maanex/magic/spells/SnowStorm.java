package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class SnowStorm extends MagicSpell {

	public SnowStorm() {
		super(63, "Schneesturm", "Brrrrrrrr!", 2, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Random r = new Random();
		int i = 50;
		while (i-- > 0)
			caster.getMCPlayer().launchProjectile(Snowball.class, caster.getMCPlayer().getLocation().getDirection().add(v(r)).normalize().multiply((double) r.nextInt(20) / 10));

		takeMana(caster, mods);
	}

	private Vector v(Random r) {
		return new Vector(r(r), r(r), r(r));
	}

	private double r(Random r) {
		return ((double) r.nextInt(20) - 10) / 50;
	}

}
