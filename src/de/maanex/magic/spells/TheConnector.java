package de.maanex.magic.spells;


import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import de.maanex.magic.MagicPlayer;
import de.maanex.magic.MagicSpell;
import de.maanex.magic.WandModifiers;
import de.maanex.magic.enumeri.SpellCategory;
import de.maanex.magic.enumeri.SpellRarity;
import de.maanex.magic.enumeri.SpellType;
import de.maanex.magic.enumeri.WandType;
import de.maanex.utils.ParticleUtil;


public class TheConnector extends MagicSpell {

	public TheConnector() {
		super(32, "Der Verbinder", "Richtig eingesetzt, vermag man ihm groﬂe Macht!", 5, 3, SpellType.ACTIVE, SpellCategory.COMBAT, SpellRarity.RARE);
	}

	HashMap<MagicPlayer, Location> pos = new HashMap<>();

	@Override
	protected void onCastPerform(MagicPlayer caster, WandType type, WandModifiers mods) {
		Block target = caster.getMCPlayer().getTargetBlock(null, mods.getEnergy() / 2);
		if (target == null || target.isEmpty()) return;

		if (!pos.containsKey(caster)) {
			pos.put(caster, target.getLocation());
			ParticleUtil.spawn(Particle.END_ROD, target.getLocation().clone().add(.5, .5, .5), 100, .01, .4, .4, .4);
		} else {
			Location l1 = pos.get(caster);
			Location l2 = target.getLocation();
			pos.remove(caster);

			if (!l1.getWorld().equals(l2.getWorld())) return;
			if (l1.distance(l2) > mods.getEnergy()) {
				caster.getMCPlayer().sendMessage("ß8Zu weit auseinander!");
				return;
			}
			if (l1.distance(l2) < 10) {
				caster.getMCPlayer().sendMessage("ß8Zu nah beinander!");
				return;
			}

			int x = l1.getBlockX() - l2.getBlockX();
			int y = l1.getBlockY() - l2.getBlockY();
			int z = l1.getBlockZ() - l2.getBlockZ();

			double g = l1.distance(l2);
			for (double d = 0; d < l1.distance(l2); d += .5) {
				Location l = l1.clone().add(-x * d / g + .5, -y * d / g + .5, -z * d / g + .5);
				ParticleUtil.spawn(Particle.END_ROD, l, 30, .01, .4, .4, .4);
				for (Entity e : l.getWorld().getNearbyEntities(l, 2, 2, 2)) {
					if (e instanceof LivingEntity) {
						((LivingEntity) e).damage(20, caster.getMCPlayer());
					}
				}
			}

			takeMana(caster, mods);
		}
	}

}
