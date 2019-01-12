package de.maanex.magic.spells;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Arrow.PickupStatus;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.basic.Element;
import de.maanex.magic.spell.MagicSpell;
import de.maanex.magic.spell.SpellCategory;
import de.maanex.magic.spell.SpellRarity;
import de.maanex.magic.spell.SpellType;
import de.maanex.magic.wands.WandType;
import de.maanex.magic.wands.WandValues;
import de.maanex.main.Main;


public class ArrowAirstrike extends MagicSpell {

	public ArrowAirstrike() {
		super(76, "Luftangriff Pfeilsturm", "*tröt*", 4, 30, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.EPIC, "Reichweite :air:");
	}

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandValues val) {
		if (val.getElement(Element.AIR) <= 0) return;
		Block target = caster.getMCPlayer().getTargetBlock(null, val.getElement(Element.AIR) * 10);
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

		takeMana(caster, val);
	}

	private Vector v(Random r) {
		return new Vector(r(r), r(r), r(r));
	}

	private double r(Random r) {
		return ((double) r.nextInt(20) - 10) / 50;
	}

}
