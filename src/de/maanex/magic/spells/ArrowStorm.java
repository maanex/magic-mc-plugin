package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic._legacy.LegacyWandModifiers;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;


public class ArrowStorm extends MagicSpell {

	public ArrowStorm() {
		super(7, "Pfeilsturm", "Au, au, aua, AUTSCH! STOP!", 4, 20, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, LegacyWandModifiers mods) {
		Random r = new Random();
		int i = 30;
		while (i-- > 0) {
			Arrow a = caster.getMCPlayer().launchProjectile(Arrow.class, caster.getMCPlayer().getLocation().getDirection().add(v(r)).normalize());
			a.setPickupStatus(PickupStatus.CREATIVE_ONLY);
			a.setTicksLived(20 * 60 * 5 - 40);
			a.setCritical(true);
			a.setKnockbackStrength(2);
		}

		takeMana(caster, mods);
	}

	private Vector v(Random r) {
		return new Vector(r(r), r(r), r(r));
	}

	private double r(Random r) {
		return ((double) r.nextInt(20) - 10) / 50;
	}

}
