package de.maanex.magic.missile;


import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.main.Main;
import de.maanex.utils.ParticleUtil;


public class WaterSplashMissile extends MagicMissile {

	private Location	dir;
	private int			life;

	public WaterSplashMissile(Location startPos, MagicPlayer sender, Location direction, int life) {
		super(startPos, sender);
		this.dir = direction;
		this.life = life;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (life-- <= 0) {
			destroy();
			return;
		}

		float p = dir.getPitch() - 90;
		if (p > 0) p -= 3;
		if (p < 0) p += 3;
		if (Math.abs(p) <= 2) p = 0;
		dir.setPitch(p + 90);
		position.add(dir.getDirection().multiply(.8));

		Collection<Entity> e = position.getWorld().getNearbyEntities(position, 2, .5, 2);
		if (!e.isEmpty()) e.forEach(n -> {
			if (!n.equals(sender.getMCPlayer())) {
				n.setVelocity(new Vector(0, 0, 0));

				if (n instanceof LivingEntity) {
					((LivingEntity) n).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * 5, 2));
					((LivingEntity) n).damage(6, sender.getMCPlayer());
					((LivingEntity) n).setRemainingAir(0);
				}

				ParticleUtil.spawn(Particle.WATER_BUBBLE, n.getLocation(), 200, 1, 1, 1, 1);

				destroy();
			}
		});

		if (position.getBlock().getType().isSolid()) return;

		Bukkit.getOnlinePlayers().forEach(pl -> pl.sendBlockChange(position, Material.WATER, (byte) 0));
		Block b = position.clone().getBlock();

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
			Material m = b.getType();
			byte d = b.getData();
			Bukkit.getOnlinePlayers().forEach(pl -> pl.sendBlockChange(b.getLocation(), m, d));
		}, 20 * 5 + new Random().nextInt(40));
	}

}
