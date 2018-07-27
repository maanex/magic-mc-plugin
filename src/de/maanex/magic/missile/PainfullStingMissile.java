package de.maanex.magic.missile;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.ParticleUtil;


public class PainfullStingMissile extends MagicMissile {

	public PainfullStingMissile(Location startPos, MagicPlayer sender, Entity target) {
		super(startPos, sender);
		this.target = target;
	}

	private int fuel = 20 * 15;

	private Entity target;

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (fuel-- <= 0 || target == null || target.getLocation().distance(position) <= 1) {
			if (target.getLocation().distance(position) <= 1 && target instanceof LivingEntity) {
				try {
					((LivingEntity) target).damage(5, sender.getMCPlayer());
				} catch (Exception e) {}
			}
			destroy();
			ParticleUtil.spawn(Particle.BLOCK_CRACK, position, 300, .2, 2, 2, 2, Material.DIAMOND_BLOCK.getData());
			return;
		}

		Vector v = target.getLocation().clone().subtract(position).toVector().normalize();
		position.setDirection(position.getDirection().add(v.multiply(.2)));

		position.add(position.getDirection().multiply(.5));

		ParticleUtil.spawn(Particle.BLOCK_CRACK, position, 10, .2, 0, 0, 0, Material.DIAMOND_BLOCK.getData());
		ParticleUtil.spawn(Particle.DRIP_LAVA, position, 1, 1, 0, 0, 0);
	}

}
