package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class SnowStorm extends MagicSpell {

	public SnowStorm() {
		super(63, "Schneesturm", "Brrrrrrrr!", 2, 4, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
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
