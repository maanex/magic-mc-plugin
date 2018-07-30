package de.maanex.magic.missile;


import java.util.Collection;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.ParticleUtil;


public class StunMissile extends MagicMissile {

	private Location	dir;
	private int			life;

	public StunMissile(Location startPos, MagicPlayer sender, Location direction, int life) {
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

		position.add(dir.getDirection().multiply(.8));

		Collection<Entity> e = position.getWorld().getNearbyEntities(position, 1, 1, 1);
		if (!e.isEmpty()) e.forEach(n -> {
			if (!n.equals(sender.getMCPlayer())) {
				n.setVelocity(new Vector(0, 0, 0));
				if (n instanceof LivingEntity) ((LivingEntity) n).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 250));

				ParticleUtil.spawn(Particle.REDSTONE, position, 200, 1, .5, .5, .5, Color.fromRGB(new Random().nextInt(0xffffff)), 1f);

				destroy();
			}
		});

		ParticleUtil.spawn(org.bukkit.Particle.SPELL_INSTANT, position, 1, 0, 0, 0, 0);

		Location pi = dir.clone();
		pi.setPitch(dir.getPitch() + 90);

		Location ya = dir.clone();
		ya.setYaw(dir.getYaw() + 90);

		Location pa2loc = position.clone();
		pa2loc.add(pi.getDirection().multiply(Math.sin(life * .2)));
		pa2loc.add(ya.getDirection().multiply(Math.cos(life * .2)));

		ParticleUtil.spawn(Particle.REDSTONE, pa2loc, 1, 1, 0, 0, 0, Color.ORANGE, 1f);

		Location pa3loc = position.clone();
		pa3loc.add(pi.getDirection().multiply(-Math.sin(life * .2)));
		pa3loc.add(ya.getDirection().multiply(-Math.cos(life * .2)));
		ParticleUtil.spawn(Particle.REDSTONE, pa3loc, 1, 1, 0, 0, 0, Color.PURPLE, 1f);
	}

}
