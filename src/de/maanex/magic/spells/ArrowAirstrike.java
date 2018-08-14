package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
import de.maanex.main.Main;


public class ArrowAirstrike extends MagicSpell {

	public ArrowAirstrike() {
		super(79, "Luftangriff Pfeilsturm", "*tröt*", 4, 30, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC);
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy());
		Random r = new Random();
		int i = 100;
		while (i-- > 0) {
			Location l = target.getLocation().clone().add(r.nextInt(5) - 2, 100 + r.nextInt(100), r.nextInt(5) - 2).add(v(r).multiply(r.nextInt(30) / 5));
			Arrow a = target.getWorld().spawn(l, Arrow.class);
			a.setVelocity(new Vector(0, -2, 0));
			a.setShooter(caster.getMCPlayer());
			a.setPickupStatus(PickupStatus.CREATIVE_ONLY);
			a.setTicksLived(20 * 60 * 5 - 40);

			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> a.remove(), 200 + r.nextInt(80));
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
