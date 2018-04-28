package de.maanex.magic.missile;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


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
			Particle pa = new Particle(EnumParticle.BLOCK_CRACK, position, true, 2, 2, 2, .2f, 300);
			pa.setC(Material.DIAMOND_BLOCK.getId());
			pa.sendAll();
			return;
		}

		Vector v = target.getLocation().clone().subtract(position).toVector().normalize();
		position.setDirection(position.getDirection().add(v.multiply(.2)));

		position.add(position.getDirection().multiply(.5));

		Particle pa = new Particle(EnumParticle.BLOCK_CRACK, position, true, 0, 0, 0, .2f, 10);
		pa.setC(Material.DIAMOND_BLOCK.getId());
		pa.sendAll();
		Particle pb = new Particle(EnumParticle.DRIP_LAVA, position, true, 0, 0, 0, 1, 1);
		pb.sendAll();
	}

}
