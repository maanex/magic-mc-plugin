package de.maanex.magic.missile;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.maanex.magic.MagicPlayer;
import de.maanex.main.Main;


public class MagmaMissile extends MagicMissile {

	public MagmaMissile(Location startPos, MagicPlayer sender, Entity target) {
		super(startPos, sender);
		this.target = target;
		position.setDirection(new Vector(0, 2, 0));
	}

	private int	fuel		= 20 * 30;
	private int	airTicks	= 0;

	private Entity target;

	@SuppressWarnings("deprecation")
	@Override
	public void tick() {
		if (fuel-- <= 0 || target == null || target.getLocation().distance(position) <= 1) {
			if (target.getLocation().distance(position) <= 1 && target instanceof LivingEntity) {
				try {
					((LivingEntity) target).damage(7, sender.getMCPlayer());
					((LivingEntity) target).setFireTicks(60);
				} catch (Exception e) {}
			} else {
				destroy();
			}
			return;
		}

		if (position.getBlock().getType().equals(Material.AIR) || position.clone().add(0, 1, 0).getBlock().getType().equals(Material.AIR)) airTicks++;
		else airTicks--;
		Vector v = target.getLocation().clone().subtract(position).toVector().normalize();
		if (airTicks > 0) v.setY(-(airTicks / 10));
		position.setDirection(position.getDirection().add(v.multiply(.1)));

		position.add(position.getDirection().multiply(.3));

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.sendBlockChange(position, Material.LAVA, (byte) 0);
			Block b = position.getBlock();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
				p.sendBlockChange(b.getLocation(), b.getType(), b.getData());
			}, 40);
		}
	}

	@Override
	public void magicRedirect(Vector vector) {
		position.setDirection(vector);
	}

}
