package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.wandsuse.WandType;


public class ArrowStorm extends MagicSpell {

	public ArrowStorm() {
		super(7, "Pfeilsturm", "Au, au, aua, AUTSCH! STOP!", 4);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Random r = new Random();
		int i = 30;
		while (i-- > 0) {
			Arrow a = caster.getMCPlayer().launchProjectile(Arrow.class, caster.getMCPlayer().getLocation().getDirection().add(v(r)).normalize());
			a.setPickupStatus(PickupStatus.CREATIVE_ONLY);
			a.setTicksLived(20 * 60 * 5 - 40);
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
