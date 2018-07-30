package de.maanex.magic.missile;


import java.util.Collection;
import java.util.function.Function;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;


public class BasicMissile extends MagicMissile {

	public Location						dir;
	public int							life;
	public double						speed;
	public Function<Location, Void>		particle;
	public Function<LivingEntity, Void>	damage;

	public BasicMissile(Location startPos, MagicPlayer sender, Location direction, int life, double speed, Function<Location, Void> particle, Function<LivingEntity, Void> damage) {
		super(startPos, sender);
		this.dir = direction;
		this.life = life;
		this.speed = speed;
		this.particle = particle;
		this.damage = damage;
	}

	@Override
	public void tick() {
		if (life-- <= 0) {
			destroy();
			return;
		}

		for (int i = 0; i < 2; i++) {
			position.add(dir.getDirection().multiply(speed));

			Collection<Entity> e = position.getWorld().getNearbyEntities(position, 1, 1, 1);
			if (!e.isEmpty()) e.forEach(n -> {
				if (!n.equals(sender.getMCPlayer())) {
					n.setVelocity(new Vector(0, 0, 0));
					if (n instanceof LivingEntity) damage.apply((LivingEntity) n);

					destroy();
				}
			});

			particle.apply(position);
		}
	}
}
