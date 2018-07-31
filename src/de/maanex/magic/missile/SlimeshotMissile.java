package de.maanex.magic.missile;


import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.ParticleUtil;


public class SlimeshotMissile extends MagicMissile {

	public Location	dir;
	public double	speed;

	public SlimeshotMissile(Location startPos, MagicPlayer sender, Location direction, double speed) {
		super(startPos, sender);
		this.dir = direction;
		this.speed = speed;
	}

	@Override
	public void tick() {
		if (speed <= 0.15) {
			destroy();
			return;
		}

		for (int i = 0; i < 2; i++) {
			position.add(dir.getDirection().multiply(speed));
			dir.setPitch(Math.min(90, dir.getPitch() + 1));
			if (dir.getPitch() > 0 && position.clone().add(0, -.5, 0).getBlock().getType().isSolid()) {
				dir.setPitch(-dir.getPitch());
				speed *= .5;
			}

			Collection<Entity> e = position.getWorld().getNearbyEntities(position, 1, 1, 1);
			if (!e.isEmpty()) e.forEach(n -> {
				if (!n.equals(sender.getMCPlayer())) {
					n.setVelocity(new Vector(0, 0, 0));
					if (n instanceof LivingEntity) {
						LivingEntity en = (LivingEntity) n;

						en.damage(5, sender.getMCPlayer());
						en.setVelocity(dir.getDirection().multiply(.4).add(new Vector(0, 2, 0)));
						en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 80, 3));
					}

					destroy();
				}
			});

			ParticleUtil.spawn(Particle.SLIME, position, 10, .01, .1, .1, .1);
		}
	}

}
