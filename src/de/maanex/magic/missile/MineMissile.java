package de.maanex.magic.missile;


import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;

import de.maanex.magic.MagicPlayer;
import de.maanex.utils.ParticleUtil;
import de.maanex.utils.ParticleUtil.ParticlePreset;


public class MineMissile extends MagicMissile {

	private int				lifedur, damage;
	private double			rad;
	private ParticlePreset	particle;
	private PotionEffect[]	effects;

	private Runnable				tickRun;
	private EntityEnterMineListener	list;

	public MineMissile(Location pos, MagicPlayer sender, int lifedur, int damage, double rad, ParticlePreset particle, PotionEffect... effects) {
		super(pos, sender);
		this.lifedur = lifedur;
		this.damage = damage;
		this.rad = rad;
		this.particle = particle;
		this.effects = effects;
	}

	public void setRunTick(Runnable r) {
		tickRun = r;
	}

	public void setListener(EntityEnterMineListener l) {
		list = l;
	}

	@Override
	public void tick() {
		if (lifedur-- <= 0) {
			destroy();
			return;
		}
		if (particle != null) ParticleUtil.spawn(particle);
		if (tickRun != null) tickRun.run();

		for (Entity e : position.getWorld().getNearbyEntities(position, rad, rad, rad)) {
			if (e.equals(sender.getMCPlayer())) continue;
			if (e instanceof LivingEntity) {
				destroy();

				if (damage > 0) ((LivingEntity) e).damage(damage, sender.getMCPlayer());
				for (PotionEffect f : effects)
					((LivingEntity) e).addPotionEffect(f);
				if (list != null) list.onEnter(e);
			}
		}
	}

	public static abstract class EntityEnterMineListener {
		public abstract void onEnter(Entity e);
	}

}
