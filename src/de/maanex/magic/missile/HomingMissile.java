package de.maanex.magic.missile;


import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.ParticleUtil;


public class HomingMissile extends MagicMissile {

	public HomingMissile(Location startPos, MagicPlayer sender, Entity target) {
		super(startPos, sender);
		this.target = target;
	}

	private int fuel = 20 * 15;

	private Entity target;

	@Override
	public void tick() {
		if (fuel-- <= 0 || target == null || target.getLocation().distance(position) <= 1) {
			if (target.getLocation().distance(position) <= 1 && target instanceof LivingEntity) {
				try {
					((LivingEntity) target).damage(7, sender.getMCPlayer());
				} catch (Exception e) {}
			}
			destroy();
			ParticleUtil.spawn(Particle.EXPLOSION_LARGE, position, 10, .2, 2, 2, 2);
			return;
		}

		Vector v = target.getLocation().clone().subtract(position).toVector().normalize();
		position.setDirection(position.getDirection().add(v.multiply(.17)));

		position.add(position.getDirection().multiply(.8));

		ParticleUtil.spawn(Particle.REDSTONE, position, 3, 1, .01, .01, .01, Color.BLACK, 1f);
		ParticleUtil.spawn(Particle.EXPLOSION_NORMAL, position, 5, .02, .2, .2, .2);
	}

}
