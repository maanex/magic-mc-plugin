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
	public BlockHitBehaviour			blockhit;
	public Function<Location, Void>		particle;
	public Function<LivingEntity, Void>	damage;

	public Function<Location, Void> destroy;

	public BasicMissile(Location startPos, MagicPlayer sender, Location direction, int life, double speed, BlockHitBehaviour blockhit, Function<Location, Void> particle,
			Function<LivingEntity, Void> damage) {
		super(startPos, sender);
		this.dir = direction;
		this.life = life;
		this.speed = speed;
		this.blockhit = blockhit;
		this.particle = particle;
		this.damage = damage;
	}

	public BasicMissile(MagicPlayer sender, int life, double speed, BlockHitBehaviour blockhit, Function<Location, Void> particle, Function<LivingEntity, Void> damage) {
		this(sender.getMCPlayer().getEyeLocation(), sender, sender.getMCPlayer().getLocation(), life, speed, blockhit, particle, damage);
	}

	@Override
	public void tick() {
		if (life-- <= 0) {
			if (destroy != null) destroy.apply(position);
			destroy();
			return;
		}

		for (int i = 0; i < 2; i++) {
			position.add(dir.getDirection().multiply(speed));
			if (!blockhit.equals(BlockHitBehaviour.PASS_THRU)) {
				if (blockhit.equals(BlockHitBehaviour.ABSORB)) {
					if (position.getBlock().getType().isSolid()) {
						if (destroy != null) destroy.apply(position);
						destroy();
						return;
					}
				} else if (blockhit.equals(BlockHitBehaviour.REFLECT)) {
					if (position.clone().add(speed, 0, 0).getBlock().getType().isSolid()) dir.setYaw(90 + (-90 - dir.getYaw()));
					if (position.clone().add(-speed, 0, 0).getBlock().getType().isSolid()) dir.setYaw(-90 + (90 - dir.getYaw()));

					if (position.clone().add(0, 0, speed).getBlock().getType().isSolid()) dir.setYaw(180 - dir.getYaw());
					if (position.clone().add(0, 0, -speed).getBlock().getType().isSolid()) dir.setYaw(-dir.getYaw() - 180);

					if (position.clone().add(0, speed, 0).getBlock().getType().isSolid()) dir.setPitch(-dir.getPitch());
					if (position.clone().add(0, -speed, 0).getBlock().getType().isSolid()) dir.setPitch(-dir.getPitch());
				}
			}

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

	/*
	 * 
	 */

	public static enum BlockHitBehaviour {
		PASS_THRU, ABSORB, REFLECT
	}
}
