package de.maanex.magic.missile;


import java.util.Collection;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.ParticleUtil;


public class HookMissile extends MagicMissile {

	private Location	dir;
	private int			life;

	public HookMissile(Location startPos, MagicPlayer sender, Location direction, int life) {
		super(startPos, sender);
		this.dir = direction;
		this.life = life;
	}

	@Override
	public void tick() {
		if (life-- <= 0) {
			destroy();
			return;
		}

		for (int i = 0; i < 3; i++) {
			position.add(dir.getDirection().multiply(.6));

			Collection<Entity> e = position.getWorld().getNearbyEntities(position, 1, 1, 1);
			if (!e.isEmpty()) e.forEach(n -> {
				if (!n.equals(sender.getMCPlayer())) {
					n.setVelocity(new Vector(0, 0, 0));
					if (n instanceof LivingEntity) ((LivingEntity) n).setVelocity(dir.getDirection().multiply(-2));

					int c = new Random().nextInt(0x44);
					ParticleUtil.spawn(Particle.REDSTONE, position, 100, 1, .3, .3, .3, Color.fromRGB(c, c, c));

					destroy();
				}
			});

			ParticleUtil.spawn(Particle.TOWN_AURA, position, 15, 0, 0.02, 0.02, 0.02);
		}
	}

}
