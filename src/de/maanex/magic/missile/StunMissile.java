package de.maanex.magic.missile;


import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.Particle;
import net.minecraft.server.v1_12_R1.EnumParticle;


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

				new Particle(EnumParticle.REDSTONE, n.getLocation(), true, .5f, .5f, .5f, 1, 200).sendAll();

				destroy();
			}
		});

		Particle pa1 = new Particle(EnumParticle.SPELL_INSTANT, position, true, 0, 0, 0, 0, 1);
		pa1.sendAll();

		Location pi = dir.clone();
		pi.setPitch(dir.getPitch() + 90);

		Location ya = dir.clone();
		ya.setYaw(dir.getYaw() + 90);

		Location pa2loc = position.clone();
		pa2loc.add(pi.getDirection().multiply(Math.sin(life * .2)));
		pa2loc.add(ya.getDirection().multiply(Math.cos(life * .2)));
		Particle pa2 = new Particle(EnumParticle.REDSTONE, pa2loc, true, 1, 1, 0, 1, 0);
		pa2.sendAll();

		Location pa3loc = position.clone();
		pa3loc.add(pi.getDirection().multiply(-Math.sin(life * .2)));
		pa3loc.add(ya.getDirection().multiply(-Math.cos(life * .2)));
		Particle pa3 = new Particle(EnumParticle.REDSTONE, pa3loc, true, 0, 0, 1, 1, 0);
		pa3.sendAll();
	}

}
