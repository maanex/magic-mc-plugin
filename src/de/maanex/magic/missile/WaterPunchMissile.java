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


public class WaterPunchMissile extends MagicMissile {

	private Location	dir;
	private int			life;

	public WaterPunchMissile(Location startPos, MagicPlayer sender, Location direction, int life) {
		super(startPos, sender);
		this.dir = direction;
		this.life = life;
	}

	@Override
	public void tick() {
		int rep = 10;
		while (rep-- > 0) {
			if (life-- <= 0) {
				destroy();
				return;
			}

			position.add(dir.getDirection().multiply(.2));

			Collection<Entity> e = position.getWorld().getNearbyEntities(position, .5, .5, .5);
			if (!e.isEmpty()) e.forEach(n -> {
				if (!n.equals(sender.getMCPlayer())) {
					n.setVelocity(new Vector(0, 0, 0));
					if (n instanceof LivingEntity) {
						((LivingEntity) n).addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 80, 3));
						((LivingEntity) n).setVelocity(dir.getDirection().multiply(3));
						((LivingEntity) n).damage(4, sender.getMCPlayer());
					}

					new Particle(EnumParticle.WATER_BUBBLE, n.getLocation(), true, .3f, .3f, .3f, 1, 70).sendAll();

					destroy();
				}
			});

			Location pi = dir.clone();
			pi.setPitch(dir.getPitch() + 90);
			Location ya = dir.clone();
			ya.setYaw(dir.getYaw() + 90);
			Location paloc = position.clone();
			paloc.add(pi.getDirection().multiply(Math.sin(life * .2) * .5));
			paloc.add(ya.getDirection().multiply(Math.cos(life * .2) * .5));
			Particle pa = new Particle(EnumParticle.DRIP_WATER, paloc, true, .01f, .01f, .01f, .01f, 2);
			pa.sendAll();
		}
	}

}
