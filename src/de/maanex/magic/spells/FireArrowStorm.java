package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;


public class FireArrowStorm extends MagicSpell {

	public FireArrowStorm() {
		super(56, "Feuerpfeilsturm", "Au, au, aua, AUTSCH! STOP! HEIẞ!", 4, 20, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.VERY_RARE);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Random r = new Random();
		int i = 30;
		while (i-- > 0) {
			Arrow a = caster.getMCPlayer().launchProjectile(Arrow.class, caster.getMCPlayer().getLocation().getDirection().add(v(r)).normalize());
			a.setPickupStatus(PickupStatus.CREATIVE_ONLY);
			a.setTicksLived(20 * 60 * 5 - 40);
			a.setCritical(true);
			a.setFireTicks(200);
			a.setKnockbackStrength(1);
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
